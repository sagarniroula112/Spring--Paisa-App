package com.sagar.paisabanking.controller;

import com.sagar.paisabanking.model.Account;
import com.sagar.paisabanking.model.Transaction;
import com.sagar.paisabanking.model.User;
import com.sagar.paisabanking.repo.AccountRepo;
import com.sagar.paisabanking.repo.TransactionRepo;
import com.sagar.paisabanking.service.AccountService;
import com.sagar.paisabanking.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    private String index() {
        return "index";
    }

    @GetMapping("/dashboard")
    private String dashboard(HttpSession session, Model model, HttpServletRequest request) {
        // Retrieve the active user from the session
        User activeUser = (User) session.getAttribute("activeUser");

        if (activeUser != null) {
            // Populate the model with user details
            model.addAttribute("request", request);
            model.addAttribute("username", activeUser.getUsername());
            model.addAttribute("balance", activeUser.getAccount().getBalance());
            model.addAttribute("accuredInterest", activeUser.getAccount().getAccruedInterest());
            model.addAttribute("interestRate", activeUser.getAccount().getInterestRate());

            return "Dashboard";
        }

        // Redirect to login if the session does not have an active user
        return "redirect:/login";
    }


    @GetMapping("/fundtransfer")
    public String fundTransfer(HttpSession session, Model model) {
        User activeUser = (User) session.getAttribute("activeUser");

        if (activeUser != null) {
            model.addAttribute("senderAccId", activeUser.getAccount().getAccountId());
            return "FundTransfer";
        }

        return "redirect:/login";
    }

    @PostMapping("/fundtransfer")
    public String transferFunds(
            @RequestParam int receiverAccNo,
            @RequestParam String lastName,
            @RequestParam Double amountExchanged,
            HttpSession session,
            Model model) {
        // Retrieve the sender's account
        User sender = (User) session.getAttribute("activeUser");
        Account senderAccount = sender.getAccount();

        // Ensure the sender has enough balance
        if (senderAccount.getBalance() < amountExchanged) {
            model.addAttribute("error", "Insufficient funds for the transaction.");
            return "FundTransfer";
        }

        // Retrieve the recipient's account
        Account receiverAccount = accountRepo.findByAccountId(receiverAccNo);
        if (receiverAccount == null) {
            model.addAttribute("error", "Recipient account not found.");
            return "FundTransfer";
        }

        // Perform the transaction
        senderAccount.setBalance(senderAccount.getBalance() - amountExchanged);
        receiverAccount.setBalance(receiverAccount.getBalance() + amountExchanged);

        // Save the updated accounts
        accountService.updateAccount(senderAccount);
        accountService.updateAccount(receiverAccount);

        // Optionally, save the transaction details
        Transaction transaction = new Transaction();
        transaction.setAccount(senderAccount);
        transaction.setSenderAccNo(sender.getAccount().getAccountId());
        transaction.setReceiverAccNo(receiverAccNo);
        transaction.setAmountExchanged(amountExchanged);

        transactionService.addTransaction(transaction);

        // Add success message and redirect to Dashboard
        model.addAttribute("success", "Funds transferred successfully!");
        return "redirect:/dashboard";
    }

    @GetMapping("/viewstatement")
    public String viewStatement(Model model, HttpSession session) {
        User activeUser = (User) session.getAttribute("activeUser");
        if (activeUser == null) {
            return "redirect:/login";
        }

        List<Transaction> transactions = transactionRepo.findAllBySenderAccNo(activeUser.getAccount().getAccountId());
        model.addAttribute("transactions", transactions);
        return "ViewStatement";
    }




}
