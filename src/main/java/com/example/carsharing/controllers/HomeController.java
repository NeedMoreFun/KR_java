package com.example.carsharing.controllers;

import com.example.carsharing.entities.Order;
import com.example.carsharing.entities.User;
import com.example.carsharing.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = {"/index", "/"})
    public String home() {
        return "index";
    }

    @GetMapping("/rent_car_{number}")
    public String rentCarWithNumber(@AuthenticationPrincipal User user, @PathVariable int number, RedirectAttributes redirectAttributes) {
        orderService.addOrderToUser(user,number);
        redirectAttributes.addFlashAttribute("rentSuccess"+number,true);
        redirectAttributes.addFlashAttribute("message","Car rent successful");
        return "redirect:/index#"+number;
    }
}
