package com.sagar.paisabanking.controller;

import com.sagar.paisabanking.model.User;
import com.sagar.paisabanking.repo.CardRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CardController {

    @Autowired
    private CardRepo cardRepo;

    @GetMapping("/card/request")
    public String cardRequest(Model model, HttpServletRequest request, HttpSession session) {

        User activeUser = (User) session.getAttribute("activeUser");

        if (activeUser != null) {
            model.addAttribute("currentUri", request.getRequestURI());
            return "CardRequest";
        }

        return "redirect:/login";
    }

    @GetMapping("/cards")
    public String cards(Model model, HttpServletRequest request, HttpSession session) {
        User activeUser = (User) session.getAttribute("activeUser");

        if (activeUser != null) {
            model.addAttribute("currentUri", request.getRequestURI());
            model.addAttribute("cards", cardRepo.findAllByAccount(activeUser.getAccount()));
            return "YourCards";
        }

        return "redirect:/login";
    }

    @GetMapping("/card/requestSubmission")
    public String postCardRequest(HttpSession session){
        User activeUser = (User) session.getAttribute("activeUser");

        if (activeUser != null) {
            return "CardRequestSubmitted";
        }

        return "redirect:/login";
    }
}
