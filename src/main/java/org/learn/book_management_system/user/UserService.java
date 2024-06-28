package org.learn.book_management_system.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.learn.book_management_system.role.Role;
import org.learn.book_management_system.role.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserDTO> save(UserDTO user) {
        try {
            if (user == null) {
                log.info("UserService.save() :: Can not save null user");
                return Optional.empty();
            }
            UserModel model = convertToModel(user);
            log.info("UserService.save() :: Saving user {}", user.email());
            model.setPassword(passwordEncoder.encode(model.getPassword()));
            UserModel savedUser = userRepository.save(model);

            log.info("UserService.save() :: User saved {}", user.email());
            return convertToDTO(savedUser);
        } catch (Exception ex) {
            log.error("UserService.save() :: Failed to save user. error: {}", ex.getMessage());
            return Optional.empty();
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

    public Optional<UserModel> findModelById(int id) {
        if (id == 0) {
            log.warn("UserService::findModelById(} :: id is 0");
            return Optional.empty();
        }

        return userRepository.findById(id);
    }

    private Optional<UserDTO> convertToDTO(UserModel model) {
        if (model == null) {
            return Optional.empty();
        }

        return Optional.of(new UserDTO(model.getId(), model.getFullName(), model.getEmail(), model.getPassword(),
                model.getDisplayName(), model.getThoughts(), model.getRole().getRole(),
                model.getStartDate().toLocalDateTime()));
    }

    private UserModel convertToModel(UserDTO dto) {

        UserModel model = new UserModel();
        model.setId(dto.id());
        model.setFullName(dto.fullName());
        model.setEmail(dto.email());
        model.setDisplayName(dto.displayName());
        model.setPassword(dto.password());
        model.setThoughts(dto.thoughts());

        String roleId = dto.role();
        Optional<Role> role = roleService.getModelByRole(roleId);
        if (role.isEmpty()) {
            log.warn("UserService.convertToModel() :: Role not fount with id {}", role);
            return new UserModel();
        }

        model.setRole(role.get());

        return model;
    }
}
