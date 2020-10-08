package com.expense.manager.web.restcontrollers;

import com.expense.manager.Pojo.TransactionsFilter;
import com.expense.manager.model.Transaction;
import com.expense.manager.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/calculate")
public class CalculateController {

    private final TransactionService transactionService;

    public CalculateController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/sum/{type}")
    public Long calculateBalanceBasedOnDate(@RequestBody TransactionsFilter transactionsFilter,
                                            Principal principal, @PathVariable String type) {

        String username = principal.getName();
        LocalDate balanceFromDate = LocalDate.parse(transactionsFilter.getFromDate());
        LocalDate balanceToDate = LocalDate.parse(transactionsFilter.getToDate());
        List<Transaction> transactions = transactionService
                .findAllTransactionsByUsernameBetweenTwoDates(username, balanceFromDate, balanceToDate);

        if (type.equals("balance"))
            return transactions.stream()
                    .mapToLong(Transaction::getAmount)
                    .sum();
        else if (type.equals("income"))
            return transactions.stream()
                    .filter(transaction -> transaction.getType().equals("income"))
                    .mapToLong(Transaction::getAmount)
                    .sum();
        else
            return transactions.stream()
                    .filter(transaction -> transaction.getType().equals("outcome"))
                    .mapToLong(Transaction::getAmount)
                    .sum();
    }

}