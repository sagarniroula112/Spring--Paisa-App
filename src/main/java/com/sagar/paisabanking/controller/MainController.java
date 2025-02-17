package com.sagar.paisabanking.controller;

import com.sagar.paisabanking.model.User;
import com.sagar.paisabanking.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    private String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    private String dashboard(HttpSession session, Model model, HttpServletRequest request) {

        User sessionUser = (User) session.getAttribute("activeUser");

        if (sessionUser != null) {
            User activeUser = userService.getUserById(sessionUser.getId());

            // Update the session with the latest user object
            session.setAttribute("activeUser", activeUser);

            model.addAttribute("request", request);
            model.addAttribute("username", activeUser.getUsername());
            model.addAttribute("balance", activeUser.getAccount().getBalance());
            model.addAttribute("accruedInterest", activeUser.getAccount().getAccruedInterest());
            model.addAttribute("interestRate", activeUser.getAccount().getInterestRate());

            model.addAttribute("currentUri", request.getRequestURI());
            return "Dashboard";
        }

        return "redirect:/login";
    }
}
