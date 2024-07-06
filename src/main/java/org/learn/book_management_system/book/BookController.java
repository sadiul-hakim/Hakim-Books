package org.learn.book_management_system.book;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.learn.book_management_system.author.AuthorService;
import org.learn.book_management_system.category.BookCategoryService;
import org.learn.book_management_system.role.RoleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/book/v1")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final BookCategoryService categoryService;

    @PostMapping(value = "/")
    public String save(@ModelAttribute @Valid BookDTO bookDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addBook";
        }

        Optional<BookDTO> save = bookService.save(bookDTO);
        model.addAttribute("books", Collections.emptyList());
        model.addAttribute("dto", new BookDTO());
        model.addAttribute("saved", save.isPresent());
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("authors",authorService.getAll());

        return "redirect:/addBook";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id, Model model) {
        boolean deleted = bookService.deleteById(id);

        model.addAttribute("books", bookService.getAll());
        model.addAttribute("dto", new RoleDTO());
        model.addAttribute("deleted", deleted);
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("authors",authorService.getAll());

        return "redirect:/addBook";
    }
}
