package com.expense.manager.web;

import com.expense.manager.Pojo.BalanceDate;
import com.expense.manager.model.Transaction;
import com.expense.manager.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final TransactionService transactionService;

    public BalanceController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/calculate")
    public Long calculateBalanceBasedOnDate(@RequestBody BalanceDate balanceDate, Principal principal) {
        String username = principal.getName();
        LocalDate balanceFromDate = LocalDate.parse(balanceDate.getFromDate());
        LocalDate balanceToDate = LocalDate.parse(balanceDate.getToDate());
        List<Transaction> transactions = transactionService.findAllTransactionsByUsernameBetweenTwoDates(username, balanceFromDate, balanceToDate);
        return transactions.stream().mapToLong(Transaction::getAmount).sum();
    }

}