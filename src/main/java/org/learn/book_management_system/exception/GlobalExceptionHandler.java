package org.learn.book_management_system.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.learn.book_management_system.user.UserDTO;
import org.learn.book_management_system.user.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.Optional;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final UserService userService;

    @ExceptionHandler(Exception.class)
    public String anyException(Exception ex, Model model) {

        Optional<UserDTO> authenticatedUser = userService.getAuthenticatedUser();
        authenticatedUser.ifPresent(userDTO -> log.error("{} encountered error {}", userDTO.getEmail(), ex.getMessage()));
        authenticatedUser.ifPresent(userDTO -> model.addAttribute("user", authenticatedUser.get().getEmail()));

        return "redirect:/internalServerError";
    }
}
