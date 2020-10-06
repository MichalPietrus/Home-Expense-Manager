package com.expense.manager.web.restcontrollers;

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
    public Page<Transaction> filterTransactionsTableByDate(@RequestBody TransactionsFilter transactionsFilter, Principal principal,
                                                           @PathVariable Integer pageId) {

        //Filters table by date and by what type of transactions it should print, if no date is selected then it prints all transactions from the last month

        String username = principal.getName();
        Page<Transaction> transactions;
        String type = transactionsFilter.getType();
        String fromDate = transactionsFilter.getFromDate();
        String toDate = transactionsFilter.getToDate();
        LocalDate balanceFromDate;
        LocalDate balanceToDate;
        Pageable pageable = PageRequest.of(pageId, 5, Sort.by(Sort.Order.desc("date")));

        if(!fromDate.equals("NoDate") && !toDate.equals("NoDate")) {
            balanceFromDate = LocalDate.parse(fromDate);
            balanceToDate = LocalDate.parse(toDate);
        } else {
            balanceFromDate = LocalDate.now().minusMonths(1);
            balanceToDate = LocalDate.now().plusDays(1);
        }

        if (type.equals("Only Income"))
            transactions = transactionService
                    .findAllByUserUsernameAndDateBetweenAndTypeEquals(username, balanceFromDate, balanceToDate, "income", pageable);
        else if (type.equals("Only Outcome"))
            transactions = transactionService
                    .findAllByUserUsernameAndDateBetweenAndTypeEquals(username, balanceFromDate, balanceToDate, "outcome", pageable);
        else
            transactions = transactionService.findAllByUserUsernameAndDateBetweenPageable(username, balanceFromDate, balanceToDate, pageable);

        return transactions;
    }

}