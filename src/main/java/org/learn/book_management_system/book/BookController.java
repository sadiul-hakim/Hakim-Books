package org.learn.book_management_system.book;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.learn.book_management_system.author.AuthorDTO;
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
@RequestMapping("/book/v1")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView save(@RequestBody @Valid BookDTO bookDTO, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("");
            return modelAndView;
        }

        Optional<BookDTO> save = bookService.save(bookDTO);
        modelAndView.setViewName("");
        modelAndView.addObject("books", Collections.emptyList());
        return modelAndView;
    }
}
