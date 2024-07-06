package org.learn.book_management_system.role;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/role/v1")
@RequiredArgsConstructor
class RoleController {
    private final RoleService roleService;

    @PostMapping(value = "/")
    private String saveUser(@ModelAttribute @Valid RoleDTO role, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "role";
        }

        RoleDTO dto = new RoleDTO();
        Optional<RoleDTO> saved = roleService.save(role);
        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("dto", dto);
        model.addAttribute("saved", saved.isPresent());

        return "redirect:/role";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable int id, Model model) {
        boolean deleted = roleService.deleteById(id);

        model.addAttribute("roles", roleService.getAll());
        model.addAttribute("dto", new RoleDTO());
        model.addAttribute("deleted", deleted);

        return "redirect:/role";
    }
}
