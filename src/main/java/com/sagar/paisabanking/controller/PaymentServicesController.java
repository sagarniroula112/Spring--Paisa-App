package com.sagar.paisabanking.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.sagar.paisabanking.model.Account;
import com.sagar.paisabanking.model.User;
import com.sagar.paisabanking.model.Utilpayment;
import com.sagar.paisabanking.repo.UtilpaymentRepo;
import com.sagar.paisabanking.service.AccountService;


@Controller
public class PaymentServicesController {
    @Autowired
    private UtilpaymentRepo upRepo;

    @Autowired
    private AccountService accountService;

    @GetMapping("/paymentServices")
    public String paymentServices(Model model, HttpServletRequest request) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "PaymentServices";
    }

    @GetMapping("/services/topup")
    public String getTopUp(Model model) {
        return "Topup";
    }

    @GetMapping("/services/payelectricity")
    public String getElectricity(Model model) {
        return "PayElectricity";
    }

    @GetMapping("/services/paywater")
    public String getWater(Model model) {
        return "PayWater";
    }

    @GetMapping("/services/paywallet")
    public String getWallet(Model model) {
        return "PayWallet";
    }

    @PostMapping("/services/topup")
    public String postTopUp(@RequestParam String phone, @RequestParam int amount, Model model, HttpSession session) {
        User currentUser = (User)session.getAttribute("activeUser");

        String receiver;
        if (phone.startsWith("974") || phone.startsWith("986") || phone.startsWith("984") || phone.startsWith("985")) {
            receiver = "NTC";
        } else if (phone.startsWith("970") || phone.startsWith("982") || phone.startsWith("980") || phone.startsWith("981")) {
            receiver = "Ncell";
        } else {
            model.addAttribute("topupError", "Invalid phone number. Please try again.");
            return "redirect:/services/topup";
        }

        Utilpayment utilpayment = new Utilpayment();
        utilpayment.setAccount(currentUser.getAccount());
        utilpayment.setAmountExchanged(amount);
        utilpayment.setDateTime(LocalDateTime.now());
        utilpayment.setInfo(phone);
        utilpayment.setRemarks("Top up to " + phone);
        utilpayment.setReceiver(receiver);
        utilpayment.setSenderAccNo(currentUser.getAccount().getAccountId());
        utilpayment.setStatus("Success");
        utilpayment.setUpdatedBalanceSender(currentUser.getAccount().getBalance()-amount);
        utilpayment.setTransactionType("Utility");
        utilpayment.setUtilityType("Top up");

        Account acc = accountService.getAccountById(currentUser.getAccount().getAccountId());
        acc.setBalance(currentUser.getAccount().getBalance()-amount);

        accountService.updateAccount(acc);

        upRepo.save(utilpayment);

        currentUser.getAccount().setBalance(acc.getBalance());
        session.setAttribute("activeUser", currentUser);

        model.addAttribute("topupSuccess", "Top up successful. Your balance is deducted.");
        return "redirect:/services/topup";
    }

    @PostMapping("/services/payelectricity")
    public String postElectricity(@RequestParam String subscriberNo, @RequestParam int amount, Model model, HttpSession session) {
        User currentUser = (User)session.getAttribute("activeUser");

        Utilpayment utilpayment = new Utilpayment();
        utilpayment.setAccount(currentUser.getAccount());
        utilpayment.setAmountExchanged(amount);
        utilpayment.setDateTime(LocalDateTime.now());
        utilpayment.setInfo(subscriberNo);
        utilpayment.setRemarks("Subscriber no. " + subscriberNo);
        utilpayment.setReceiver("Nepal Electricity Authority");
        utilpayment.setSenderAccNo(currentUser.getAccount().getAccountId());
        utilpayment.setStatus("Success");
        utilpayment.setUpdatedBalanceSender(currentUser.getAccount().getBalance()-amount);
        utilpayment.setTransactionType("Utility");
        utilpayment.setUtilityType("Electricity Bill");

        Account acc = accountService.getAccountById(currentUser.getAccount().getAccountId());
        acc.setBalance(currentUser.getAccount().getBalance()-amount);

        accountService.updateAccount(acc);

        upRepo.save(utilpayment);

        currentUser.getAccount().setBalance(acc.getBalance());
        session.setAttribute("activeUser", currentUser);

        model.addAttribute("paySuccess", "Electricity bill payment successful. Your balance is deducted.");
        return "redirect:/paymentServices";
    }

    @PostMapping("/services/paywater")
    public String postWater(@RequestParam String subscriberNo, @RequestParam int amount, @RequestParam String receiver, Model model, HttpSession session) {
        User currentUser = (User)session.getAttribute("activeUser");

        Utilpayment utilpayment = new Utilpayment();
        utilpayment.setAccount(currentUser.getAccount());
        utilpayment.setAmountExchanged(amount);
        utilpayment.setDateTime(LocalDateTime.now());
        utilpayment.setInfo(subscriberNo);
        utilpayment.setRemarks("Subscriber no. " + subscriberNo);
        utilpayment.setReceiver(receiver);
        utilpayment.setSenderAccNo(currentUser.getAccount().getAccountId());
        utilpayment.setStatus("Success");
        utilpayment.setUpdatedBalanceSender(currentUser.getAccount().getBalance()-amount);
        utilpayment.setTransactionType("Utility");
        utilpayment.setUtilityType("Water Bill");

        Account acc = accountService.getAccountById(currentUser.getAccount().getAccountId());
        acc.setBalance(currentUser.getAccount().getBalance()-amount);

        accountService.updateAccount(acc);

        upRepo.save(utilpayment);

        currentUser.getAccount().setBalance(acc.getBalance());
        session.setAttribute("activeUser", currentUser);

        model.addAttribute("paySuccess", "Water bill payment successful. Your balance is deducted.");
        return "redirect:/paymentServices";
    }

    @PostMapping("/services/paywallet")
    public String postWallet(@RequestParam String phone, @RequestParam int amount, @RequestParam String receiver, Model model, HttpSession session) {
        User currentUser = (User)session.getAttribute("activeUser");

        Utilpayment utilpayment = new Utilpayment();
        utilpayment.setAccount(currentUser.getAccount());
        utilpayment.setAmountExchanged(amount);
        utilpayment.setDateTime(LocalDateTime.now());
        utilpayment.setInfo(phone);
        utilpayment.setRemarks(receiver + " no. " + phone);
        utilpayment.setReceiver(receiver);
        utilpayment.setSenderAccNo(currentUser.getAccount().getAccountId());
        utilpayment.setStatus("Success");
        utilpayment.setUpdatedBalanceSender(currentUser.getAccount().getBalance()-amount);
        utilpayment.setTransactionType("Utility");
        utilpayment.setUtilityType("Wallet Load");

        Account acc = accountService.getAccountById(currentUser.getAccount().getAccountId());
        acc.setBalance(currentUser.getAccount().getBalance()-amount);

        accountService.updateAccount(acc);

        upRepo.save(utilpayment);

        currentUser.getAccount().setBalance(acc.getBalance());
        session.setAttribute("activeUser", currentUser);

        model.addAttribute("paySuccess", "Water bill payment successful. Your balance is deducted.");
        return "redirect:/paymentServices";
    }
    
}
