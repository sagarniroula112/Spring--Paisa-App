package com.sagar.paisabanking.controller;

import com.sagar.paisabanking.model.Account;
import com.sagar.paisabanking.model.User;
import com.sagar.paisabanking.service.AccountService;
import com.sagar.paisabanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/register")
    private String register() {
        return "RegistrationForm";
    }

    @PostMapping("/register")
    private String registerPost(@ModelAttribute User user) {
        Account account = accountService.addAccount(new Account());
        account.setUser(user);

        user.setAccount(account);
        userService.addUser(user);

        return "redirect:/login";
    }
}
