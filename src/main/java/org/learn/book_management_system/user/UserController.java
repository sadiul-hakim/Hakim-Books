package org.learn.book_management_system.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ModelAndView saveUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("");
            return modelAndView;
        }

        Optional<UserDTO> savedUser = userService.save(userDTO);
        modelAndView.setViewName("");
        modelAndView.addObject("users", Collections.emptyList());
        return modelAndView;
    }
}
