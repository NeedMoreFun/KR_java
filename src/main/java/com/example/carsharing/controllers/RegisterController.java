package com.example.carsharing.controllers;

import com.example.carsharing.entities.User;
import com.example.carsharing.services.MailService;
import com.example.carsharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @PostMapping("/registration")
    public String registrationUser(@ModelAttribute("user") User user,
                                   Model model, RedirectAttributes redirectAttributes) {
        if (user.getUsername().length() < 5) {
            model.addAttribute("errorLenUsername", true);
            return "login";
        }
        if (!Objects.equals(user.getPassword(), user.getPasswordConfirm())) {
            model.addAttribute("errorConfPassword", true);
            return "login";
        }
        if (user.getPassword().length() < 8) {
            model.addAttribute("errorLenPassword", true);
            return "login";
        }
        if (userService.findUserByUsername(user.getUsername()) != null) {
            model.addAttribute("errorAlreadyExistsUsername", true);
            return "login";
        }
        try {
            userService.saveUser(user);
            //mailService.sendRegisterMessage(user);
            redirectAttributes.addFlashAttribute("registerSuccess", true);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorAnomaly", true);
            return "login";
        }
    }
}
