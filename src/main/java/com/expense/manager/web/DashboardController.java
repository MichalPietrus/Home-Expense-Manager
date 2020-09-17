package com.expense.manager.web;

import com.expense.manager.model.Transaction;
import com.expense.manager.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
public class DashboardController {

    private final TransactionService transactionService;

    public DashboardController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public String showDashboard(Model model, Principal principal) {
        String username = principal.getName();
        LocalDate localDateNow = LocalDate.now();
        LocalDate localDateBefore = localDateNow.minusMonths(1);
        List<Transaction> transactions = transactionService.findAllTransactionsByUsernameBetweenTwoDates(username, localDateBefore, localDateNow);
        long lastMonthBalance = transactions.stream().mapToLong(Transaction::getAmount).sum();
        model.addAttribute("lastMonthBalance", lastMonthBalance);
        return "Dashboard";
    }

}
