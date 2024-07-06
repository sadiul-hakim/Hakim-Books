package org.learn.book_management_system.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/category/v1")
@RequiredArgsConstructor
public class BookCategoryController {
    private final BookCategoryService service;

    @PostMapping(value = "/")
    public String save(@ModelAttribute @Valid BookCategoryDTO dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "bookCategory";
        }

        Optional<BookCategoryDTO> save = service.save(dto);
        model.addAttribute("categories", service.getAll());
        model.addAttribute("saved", save.isPresent());
        model.addAttribute("category", new BookCategoryDTO());

        return "redirect:/bookCategory";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable int id, Model model) {
        boolean deleted = service.deleteById(id);

        model.addAttribute("categories", service.getAll());
        model.addAttribute("category", new BookCategoryDTO());
        model.addAttribute("deleted", deleted);

        return "redirect:/bookCategory";
    }
}
