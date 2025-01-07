package com.sagar.paisabanking.controller;

import com.sagar.paisabanking.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    private String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    private String dashboard(HttpSession session, Model model, HttpServletRequest request) {

        User activeUser = (User) session.getAttribute("activeUser");

        if (activeUser != null) {
            model.addAttribute("request", request);
            model.addAttribute("username", activeUser.getUsername());
            model.addAttribute("balance", activeUser.getAccount().getBalance());
            model.addAttribute("accuredInterest", activeUser.getAccount().getAccruedInterest());
            model.addAttribute("interestRate", activeUser.getAccount().getInterestRate());

            model.addAttribute("currentUri", request.getRequestURI());
            return "Dashboard";
        }

        return "redirect:/login";
    }
}
