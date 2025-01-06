package com.sagar.paisabanking.controller;

import com.sagar.paisabanking.model.User;
import com.sagar.paisabanking.repo.UserRepo;
import com.sagar.paisabanking.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private String loginPost(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User u = userRepo.findByUsernameAndPassword(username, password);
        if (u != null) {
            session.setAttribute("activeUser", u);
            session.setMaxInactiveInterval(300);
            return "redirect:/dashboard"; // Redirect to the updated /dashboard endpoint
        }
        return "LoginForm"; // Stay on the login page for invalid credentials
    }

    @GetMapping("/logout")
    private String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
