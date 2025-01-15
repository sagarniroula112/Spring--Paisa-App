package com.sagar.paisabanking.controller;

import com.sagar.paisabanking.model.Account;
import com.sagar.paisabanking.model.Transaction;
import com.sagar.paisabanking.model.User;
import com.sagar.paisabanking.model.Utilpayment;
import com.sagar.paisabanking.repo.AccountRepo;
import com.sagar.paisabanking.repo.TransactionRepo;
import com.sagar.paisabanking.repo.UtilpaymentRepo;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class TransactionController {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UtilpaymentRepo utilpaymentRepo;

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
            Model model,
            RedirectAttributes redirectAttributes) {
        User activeUser = (User) session.getAttribute("activeUser");

        if (activeUser != null) {
            User sender = (User) session.getAttribute("activeUser");
            Account senderAccount = sender.getAccount();

            if (senderAccount.getBalance() < amountExchanged) {
                model.addAttribute("error", "Insufficient funds for the transaction.");
                return "FundTransfer";
            }

            Account receiverAccount = accountRepo.findByAccountId(receiverAccNo);
            if (receiverAccount == null) {
                redirectAttributes.addFlashAttribute("error", "Recipient account not found.");
                return "redirect:/fundtransfer";
            }

            if(receiverAccount.getAccountId() == senderAccount.getAccountId()) {
                redirectAttributes.addFlashAttribute("error", "Prohibited operation.");
                return "redirect:/fundtransfer";
            }

            senderAccount.setBalance(senderAccount.getBalance() - amountExchanged);
            receiverAccount.setBalance(receiverAccount.getBalance() + amountExchanged);

            accountService.updateAccount(senderAccount);
            accountService.updateAccount(receiverAccount);


            Transaction transaction = new Transaction();

            transaction.setAccount(senderAccount);
            transaction.setSenderAccNo(sender.getAccount().getAccountId());
            transaction.setReceiverAccNo(receiverAccNo);
            transaction.setAmountExchanged(amountExchanged);
            transaction.setDateTime(LocalDateTime.now());
            transaction.setUpdatedBalanceSender(senderAccount.getBalance());
            transaction.setUpdatedBalanceReceiver(receiverAccount.getBalance());

            transactionService.addTransaction(transaction);

            model.addAttribute("success", "Funds transferred successfully!");

            activeUser.getAccount().setBalance(transaction.getUpdatedBalanceSender());
            return "redirect:/dashboard";
        }

        return "redirect:/login";
    }

    @GetMapping("/viewstatement")
    public String viewStatement(@RequestParam("statementOption") String statementOption, Model model, HttpSession session, HttpServletRequest request) {
        User activeUser = (User) session.getAttribute("activeUser");
        if (activeUser == null) {
            return "redirect:/login";
        }

        List<Utilpayment> utilpaymentsSender = utilpaymentRepo.findAllBySenderAccNo(activeUser.getAccount().getAccountId());
        List<Transaction> transactionsSender = transactionRepo.findAllBySenderAccNo(activeUser.getAccount().getAccountId());
        List<Transaction> transactionsReceiver = transactionRepo.findAllByReceiverAccNo(activeUser.getAccount().getAccountId());

        List<Transaction> allTransactions = Stream.concat(transactionsSender.stream(), transactionsReceiver.stream())
                .sorted((t1, t2) -> t2.getDateTime().compareTo(t1.getDateTime())) // Descending order
                .collect(Collectors.toList());

        List<Utilpayment> allUtils = utilpaymentsSender.stream()
                .sorted((u1, u2) -> u2.getDateTime().compareTo(u1.getDateTime()))
                .toList();

        model.addAttribute("utilityPayments", allUtils);
        model.addAttribute("transactions", allTransactions);
        model.addAttribute("activeUser", activeUser);
        model.addAttribute("currentUri", request.getRequestURI());

        if("fundTransfer".equals(statementOption)) {
            return "ViewFundTransferStatement";
        }else if("utilityPayment".equals(statementOption)) {
            return "ViewUtilityStatement";
        }
        return "redirect:/dashboard";
    }
}
