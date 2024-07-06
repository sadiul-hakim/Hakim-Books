package org.learn.book_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.learn.book_management_system.author.AuthorDTO;
import org.learn.book_management_system.author.AuthorService;
import org.learn.book_management_system.book.BookDTO;
import org.learn.book_management_system.book.BookService;
import org.learn.book_management_system.category.BookCategoryDTO;
import org.learn.book_management_system.category.BookCategoryService;
import org.learn.book_management_system.role.RoleDTO;
import org.learn.book_management_system.role.RoleService;
import org.learn.book_management_system.user.AddBookDTO;
import org.learn.book_management_system.user.UserDTO;
import org.learn.book_management_system.user.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
class PageController {
    private final RoleService roleService;
    private final UserService userService;
    private final AuthorService authorService;
    private final BookCategoryService bookCategoryService;
    private final BookService bookService;

    @GetMapping(value = "/home", produces = MediaType.TEXT_PLAIN_VALUE)
    public String home(Model model) {

        model.addAttribute("dto", new AddBookDTO());
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("ownedBooks", bookService.getAllOfLoggedInUser());
        return "index";
    }

    @GetMapping(value = "/addBook", produces = MediaType.TEXT_PLAIN_VALUE)
    public String addBook(Model model) {

        model.addAttribute("dto", new BookDTO());
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("categories", bookCategoryService.getAll());
        model.addAttribute("authors", authorService.getAll());

        return "addBook";
    }

    @GetMapping(value = "/bookCategory", produces = MediaType.TEXT_PLAIN_VALUE)
    public String bookType(Model model) {

        BookCategoryDTO dto = new BookCategoryDTO();
        model.addAttribute("category", dto);
        model.addAttribute("categories", bookCategoryService.getAll());

        return "bookCategory";
    }

    @GetMapping(value = "/author", produces = MediaType.TEXT_PLAIN_VALUE)
    public String author(Model model) {

        AuthorDTO author = new AuthorDTO();
        List<AuthorDTO> all = authorService.getAll();
        model.addAttribute("authors", all);
        model.addAttribute("author", author);

        return "author";
    }

    @GetMapping(value = "/user", produces = MediaType.TEXT_PLAIN_VALUE)
    public String user(Model model) {

        model.addAttribute("dto", new UserDTO());
        model.addAttribute("users", userService.getAll());

        return "user";
    }

    @GetMapping(value = "/profile", produces = MediaType.TEXT_PLAIN_VALUE)
    public String profile(Model model) {

        Optional<UserDTO> authenticatedUser = userService.getAuthenticatedUser();
        if (authenticatedUser.isEmpty()) {
            List<BookDTO> all = bookService.getAll();
            model.addAttribute("books", all);
        } else {
            model.addAttribute("user", authenticatedUser.get());
        }

        return "profile";
    }

    @GetMapping(value = "/role", produces = MediaType.TEXT_PLAIN_VALUE)
    public String role(Model model) {
        RoleDTO dto = new RoleDTO();

        model.addAttribute("dto", dto);
        model.addAttribute("roles", roleService.getAll());
        return "role";
    }

    @GetMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
    public String login() {
        return "login";
    }

    @GetMapping(value = "/setting", produces = MediaType.TEXT_PLAIN_VALUE)
    public String setting() {
        return "setting";
    }

    @GetMapping(value = "/resetPassword", produces = MediaType.TEXT_PLAIN_VALUE)
    public String resetPassword() {
        return "resetPassword";
    }

    @GetMapping(value = "/internalServerError", produces = MediaType.TEXT_PLAIN_VALUE)
    public String internalServerError(Model model) {
        Optional<UserDTO> authenticatedUser = userService.getAuthenticatedUser();
        authenticatedUser.ifPresent(userDTO -> model.addAttribute("user", authenticatedUser.get().getEmail()));
        return "internalServerError";
    }
}
