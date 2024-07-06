package org.learn.book_management_system.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.learn.book_management_system.role.RoleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/user/v1")
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @PostMapping(value = "/")
    private String saveUser(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user";
        }

        Optional<UserDTO> savedUser = userService.save(userDTO);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("dto", new UserDTO());
        model.addAttribute("saved", savedUser.isPresent());

        return "redirect:/user";
    }

    @PostMapping(value = "/add-book-to-collection")
    private String addBookToCollection(@ModelAttribute @Valid AddBookDTO dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        Set<Integer> ids = dto.getBookIds();
        boolean assigned = userService.assignBookToUser(ids);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("dto", new UserDTO());
        model.addAttribute("assigned", assigned);

        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, Model model) {
        boolean deleted = userService.deleteById(id);

        model.addAttribute("users", userService.getAll());
        model.addAttribute("dto", new RoleDTO());
        model.addAttribute("deleted", deleted);

        return "redirect:/user";
    }
}
