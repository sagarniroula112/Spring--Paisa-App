package com.sagar.paisabanking.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentServicesController {
    @GetMapping("/paymentServices")
    public String paymentServices(Model model, HttpServletRequest request) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "PaymentServices";
    }
}
