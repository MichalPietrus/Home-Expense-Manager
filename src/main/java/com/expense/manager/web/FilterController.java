package com.expense.manager.web;

import com.expense.manager.Pojo.TransactionsFilter;
import com.expense.manager.model.Transaction;
import com.expense.manager.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/filter")
public class FilterController {

    private final TransactionService transactionService;

    public FilterController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/by-date/{pageId}")
    public Page<Transaction> filterTransactionsTableByDate(@RequestBody TransactionsFilter transactionsFilter, Principal principal, @PathVariable Integer pageId) {
        String username = principal.getName();
        LocalDate balanceFromDate = LocalDate.parse(transactionsFilter.getFromDate());
        LocalDate balanceToDate = LocalDate.parse(transactionsFilter.getToDate());
        String type = transactionsFilter.getType();
        Pageable pageable = PageRequest.of(pageId, 5, Sort.by(Sort.Order.desc("date")));
        Page<Transaction> transactions;
        if (type.equals("Only Income"))
            transactions = transactionService.findAllByUserUsernameAndDateBetweenAndTypeEquals(username, balanceFromDate, balanceToDate, "income", pageable);
        else if (type.equals("Only Outcome"))
            transactions = transactionService.findAllByUserUsernameAndDateBetweenAndTypeEquals(username, balanceFromDate, balanceToDate, "outcome", pageable);
        else
            transactions = transactionService.findAllByUserUsernameAndDateBetweenPageable(username, balanceFromDate, balanceToDate, pageable);
        return transactions;
    }

}