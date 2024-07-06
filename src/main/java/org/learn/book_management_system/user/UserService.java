package org.learn.book_management_system.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.learn.book_management_system.book.BookDTO;
import org.learn.book_management_system.book.BookService;
import org.learn.book_management_system.role.Role;
import org.learn.book_management_system.role.RoleService;
import org.learn.book_management_system.util.DateUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final BookService bookService;

    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder,
                       @Lazy BookService bookService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.bookService = bookService;
    }

    public Optional<UserDTO> save(UserDTO user) {
        try {
            if (user == null) {
                log.info("UserService.save() :: Can not save null user");
                return Optional.empty();
            }
            user.setRole("ROLE_OTHER");

            Optional<UserModel> optional = convertToModel(user);
            if (optional.isEmpty()) {
                log.error("UserService.save() :: model is empty!");
                return Optional.empty();
            }
            UserModel model = optional.get();
            log.info("UserService.save() :: Saving user {}", user.getEmail());
            model.setPassword(passwordEncoder.encode(model.getPassword()));
            model.setPhoto("default.png");

            UserModel savedUser = userRepository.save(model);

            log.info("UserService.save() :: User saved {}", user.getEmail());
            return convertToDTO(savedUser);
        } catch (Exception ex) {
            log.error("UserService.save() :: Failed to save user. error: {}", ex.getMessage());
            return Optional.empty();
        }
    }

    public boolean assignBookToUser(Set<Integer> bookIds) {
        try {

            for (Integer id : bookIds) {
                Optional<BookDTO> book = bookService.getById(id);
                if (book.isEmpty()) {
                    log.warn("UserService.assignBookToUser() :: book {} does not exist!", id);
                    bookIds.remove(id);
                }
            }

            Optional<UserDTO> user = getAuthenticatedUser();
            if (user.isEmpty()) {
                log.warn("UserService.assignBookToUser() :: user is empty!");
                return false;
            }

            Optional<UserModel> optional = userRepository.findById(user.get().getId());
            if (optional.isEmpty()) {
                log.warn("UserService.assignBookToUser() :: model is empty!");
                return false;
            }

            // Update Owner book list
            UserModel model = optional.get();
            Set<Integer> modifiableList = new HashSet<>(model.getOwned_books());
            modifiableList.addAll(bookIds);
            model.setOwned_books(modifiableList);

            userRepository.save(model);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    public Optional<UserDTO> findByEmail(String email) {
        if (email.isEmpty()) {
            log.warn("UserService::findByEmail(} :: Email is empty");
            return Optional.empty();
        }

        Optional<UserModel> model = userRepository.findByEmail(email);
        return model.flatMap(this::convertToDTO);
    }

    public Optional<UserDTO> findById(int id) {
        if (id == 0) {
            log.warn("UserService::findById(} :: id is 0");
            return Optional.empty();
        }

        Optional<UserModel> model = userRepository.findById(id);
        return model.flatMap(this::convertToDTO);
    }

    public boolean deleteById(int id) {
        log.info("UserService.deleteById() :: Deleting user by id {}", id);

        userRepository.deleteById(id);
        return true;
    }

    public Optional<UserModel> findModelById(int id) {
        if (id == 0) {
            log.warn("UserService::findModelById(} :: id is 0");
            return Optional.empty();
        }

        return userRepository.findById(id);
    }

    public List<UserDTO> getAll() {
        log.info("RoleService.getAll() :: getting all users.");
        List<UserModel> all = userRepository.findAll();
        return convertList(all);
    }

    public Optional<UserDTO> getAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = (CustomUserDetails) auth.getPrincipal();
        UserModel user = principal.user();

        // The user is not updated, make another call to get the latest data
        Optional<UserModel> newOptional = findModelById(user.getId());
        if (newOptional.isEmpty()) {
            log.warn("UserService.getAuthenticatedUser() :: could not get user with id {}", user.getId());
        }

        return convertToDTO(newOptional.get());
    }

    private List<UserDTO> convertList(List<UserModel> users) {
        List<UserDTO> dtos = new ArrayList<>();
        users.forEach(role -> {
            Optional<UserDTO> dto = convertToDTO(role);
            dto.ifPresent(dtos::add);
        });

        return dtos;
    }

    private Optional<UserDTO> convertToDTO(UserModel model) {
        if (model == null) {
            return Optional.empty();
        }

        return Optional.of(new UserDTO(model.getId(), model.getFullName(), model.getEmail(), "",
                model.getPhoto(), model.getThoughts(), model.getRole().getRole(),
                DateUtil.forGUI(model.getStartDate().toLocalDateTime()), model.getOwned_books()));
    }

    private Optional<UserModel> convertToModel(UserDTO dto) {

        UserModel model = new UserModel();
        model.setId(dto.getId());
        model.setFullName(dto.getFullName());
        model.setEmail(dto.getEmail());
        model.setPassword(dto.getPassword());
        model.setThoughts(dto.getThoughts());
        model.setPhoto(dto.getPhoto());

        String roleId = dto.getRole();
        Optional<Role> role = roleService.getModelByRole(roleId);
        if (role.isEmpty()) {
            log.warn("UserService.convertToModel() :: Role not fount with id {}", role);
            return Optional.empty();
        }

        model.setRole(role.get());

        return Optional.of(model);
    }
}
