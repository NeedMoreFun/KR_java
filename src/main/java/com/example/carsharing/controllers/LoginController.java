package com.example.carsharing.controllers;

import com.example.carsharing.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLogin(Model model) throws Exception {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }
}
