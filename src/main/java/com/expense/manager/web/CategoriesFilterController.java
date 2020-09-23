package com.expense.manager.web;

import com.expense.manager.Pojo.CategoryRanking;
import com.expense.manager.Pojo.TransactionsFilter;
import com.expense.manager.model.Transaction;
import com.expense.manager.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories-filter")
public class CategoriesFilterController {

    private final TransactionService transactionService;

    public CategoriesFilterController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/by-date")
    public List<CategoryRanking> filterCategoriesByDate(@RequestBody TransactionsFilter transactionsFilter, Principal principal) {

        //Finds all transactions between 2 dates

        String username = principal.getName();
        LocalDate fromDate = LocalDate.parse(transactionsFilter.getFromDate());
        LocalDate toDate = LocalDate.parse(transactionsFilter.getToDate());
        String type = transactionsFilter.getType();
        List<Transaction> transactions = transactionService.findAllTransactionsByUsernameBetweenTwoDates(username, fromDate, toDate);
        List<CategoryRanking> categoriesRanking = new ArrayList<>();


        // Calculating which three categories were the most profitable.


        if (type.equals("income")) {
            Map<String, Long> categoriesRankingIncomeMapSorted = new LinkedHashMap<>();
            long totalIncome = transactions.stream()
                    .filter(transaction -> transaction.getType().equals("income")).mapToLong(Transaction::getAmount).sum();

            Map<String, Long> categoriesRankingIncomeMap = transactions.stream()
                    .filter(transaction -> transaction.getType().equals("income"))
                    .collect(Collectors.toMap(Transaction::getCategory, Transaction::getAmount, Long::sum));

            categoriesRankingIncomeMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> categoriesRankingIncomeMapSorted.put(x.getKey(), x.getValue()));

            transactionService.getThreeCategoriesWithBiggestOrLowestValue(categoriesRankingIncomeMapSorted, categoriesRanking, totalIncome, "income");
        }


        // Calculating which three categories were the least profitable.


        else {
            Map<String, Long> categoriesRankingOutcomeMapSorted = new LinkedHashMap<>();
            long totalOutcome = transactions.stream()
                    .filter(transaction -> transaction.getType().equals("outcome")).mapToLong(Transaction::getAmount).sum();

            Map<String, Long> categoriesRankingOutcomeMap = transactions.stream()
                    .filter(transaction -> transaction.getType().equals("outcome"))
                    .collect(Collectors.toMap(Transaction::getCategory, Transaction::getAmount, Long::sum));

            categoriesRankingOutcomeMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEachOrdered(x -> categoriesRankingOutcomeMapSorted.put(x.getKey(), x.getValue()));

            transactionService.getThreeCategoriesWithBiggestOrLowestValue(categoriesRankingOutcomeMapSorted, categoriesRanking, totalOutcome, "outcome");
        }

        return categoriesRanking;
    }


}
