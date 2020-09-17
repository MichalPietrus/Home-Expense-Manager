package com.expense.manager.web;

import com.expense.manager.Pojo.BalanceDate;
import com.expense.manager.model.Transaction;
import com.expense.manager.repository.TransactionRepository;
import com.expense.manager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/filter")
public class FilterController {

    private final TransactionService transactionService;

    public FilterController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/by-date/{pageId}")
    public List<Transaction> filterTransactionsTableByDate(@RequestBody BalanceDate balanceDate, Principal principal, @PathVariable Integer pageId) {
        String username = principal.getName();
        LocalDate balanceFromDate = LocalDate.parse(balanceDate.getFromDate());
        LocalDate balanceToDate = LocalDate.parse(balanceDate.getToDate());
        Pageable pageable = PageRequest.of(pageId, 5);
        List<Transaction> transactions = transactionService.findAllByUserUsernameAndDateBetweenPageable(username,balanceFromDate,balanceToDate,pageable);
        return null;
    }





}
