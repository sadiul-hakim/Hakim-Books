package org.learn.book_management_system.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class PageController {
    @GetMapping(value = "/home", produces = MediaType.TEXT_PLAIN_VALUE)
    public String home() {
        return "index";
    }

    @GetMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
    public String login() {
        return "login";
    }

    @GetMapping(value = "/register", produces = MediaType.TEXT_PLAIN_VALUE)
    public String register() {
        return "register";
    }
}
