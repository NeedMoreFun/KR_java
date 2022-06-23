package com.example.carsharing.controllers;

import com.example.carsharing.entities.User;
import com.example.carsharing.services.OrderService;
import com.example.carsharing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/cart")
    public String getCart(@AuthenticationPrincipal User user, Model model)
    {
        orderService.sortOrders(user);
        model.addAttribute("orders",user.getCarList());
        return "cart";
    }
    @GetMapping("/stop_rent_{id}")
    public String stopRent(@AuthenticationPrincipal User user, @PathVariable int id)
    {
        orderService.stopRentCarWithId(user, id);
        return "redirect:/cart#{id}";
    }

    @GetMapping("/clear")
    public String clearFinishedOrders(@AuthenticationPrincipal User user)
    {
        orderService.clearFinishedOrders(user);
        return "redirect:/cart";
    }

}
