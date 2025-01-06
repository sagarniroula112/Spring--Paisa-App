package com.sagar.paisabanking.controller;

import com.sagar.paisabanking.model.User;
import com.sagar.paisabanking.repo.UserRepo;
import com.sagar.paisabanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    private String login() {
        return "LoginForm";
    }

    @PostMapping("/login")
    private String loginPost(@RequestParam String username, @RequestParam String password, Model model) {
        User u = userRepo.findByUsernameAndPassword(username, password);
        if(u != null) {
            model.addAttribute("username", username);
            model.addAttribute("balance", userService.getUserById(u.getId()).getAccount().getBalance());
            model.addAttribute("accuredInterest", userService.getUserById(u.getId()).getAccount().getAccruedInterest());
            model.addAttribute("interestRate", userService.getUserById(u.getId()).getAccount().getInterestRate());

            return "Dashboard";
        }
        return "LoginForm";
    }
}
