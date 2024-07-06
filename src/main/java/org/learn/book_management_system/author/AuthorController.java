package org.learn.book_management_system.author;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/author/v1")
@RequiredArgsConstructor
class AuthorController {
    private final AuthorService authorService;

    @PostMapping(value = "/")
    public String save(@ModelAttribute @Valid AuthorDTO author, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "author";
        }

        Optional<AuthorDTO> save = authorService.save(author);
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("saved", save.isPresent());
        model.addAttribute("author", new AuthorDTO());

        return "redirect:/author";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable int id,Model model){
        boolean deleted = authorService.deleteById(id);

        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("author", new AuthorDTO());
        model.addAttribute("deleted",deleted);

        return "redirect:/author";
    }
}
