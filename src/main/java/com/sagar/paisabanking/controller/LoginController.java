package com.sagar.paisabanking.controller;

import com.sagar.paisabanking.model.User;
import com.sagar.paisabanking.repo.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/login")
    private String login() {
        return "LoginForm";
    }

    @PostMapping("/login")
    private String loginPost(@RequestParam String username, @RequestParam String password, HttpSession session) {
        String pw = DigestUtils.md5DigestAsHex(password.getBytes());

        User u = userRepo.findByUsernameAndPassword(username, pw);
        if (u != null) {
            session.setAttribute("activeUser", u);
            session.setMaxInactiveInterval(300);
            return "redirect:/dashboard";
        }
        return "LoginForm";
    }

    @GetMapping("/logout")
    private String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
