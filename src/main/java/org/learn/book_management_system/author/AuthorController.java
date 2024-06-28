package org.learn.book_management_system.author;

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
@RequestMapping("/author/v1")
@RequiredArgsConstructor
class AuthorController {
    private final AuthorService authorService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView save(@RequestBody @Valid AuthorDTO author, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("");
            return modelAndView;
        }

        Optional<AuthorDTO> save = authorService.save(author);
        modelAndView.setViewName("");
        modelAndView.addObject("authors", Collections.emptyList());
        return modelAndView;
    }
}
