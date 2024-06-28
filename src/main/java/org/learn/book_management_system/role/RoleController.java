package org.learn.book_management_system.role;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/role/v1")
@RequiredArgsConstructor
class RoleController {
    private final RoleService roleService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ModelAndView saveUser(@RequestBody @Valid RoleDTO role, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("");
            return modelAndView;
        }
        Optional<RoleDTO> saved = roleService.save(role);
        modelAndView.setViewName("");
        modelAndView.addObject("roles", Collections.emptyList());
        return modelAndView;
    }
}
