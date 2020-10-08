package com.expense.manager.web.restcontrollers;

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

@RestController
@RequestMapping("/categories-filter")
public class CategoriesFilterController {

    private final TransactionService transactionService;

    public CategoriesFilterController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/by-date")
    public List<CategoryRanking> filterCategoriesByDate(@RequestBody TransactionsFilter transactionsFilter,
                                                        Principal principal) {

        //Finds all transactions between 2 dates

        String username = principal.getName();
        LocalDate fromDate = LocalDate.parse(transactionsFilter.getFromDate());
        LocalDate toDate = LocalDate.parse(transactionsFilter.getToDate());
        String type = transactionsFilter.getType();

        List<Transaction> transactions = transactionService
                .findAllTransactionsByUsernameBetweenTwoDates(username, fromDate, toDate);
        List<CategoryRanking> categoriesRanking = new ArrayList<>();

        // Calculating which three categories were the most profitable.

        if (type.equals("income")) {
            Map<String, Long> categoriesRankingIncomeMapSorted = new LinkedHashMap<>();
            long totalIncome = transactionService.getTotalTransaction(transactions, "income");

            Map<String, Long> categoriesRankingIncomeMap = transactionService.getCategoriesRankingMap(transactions, "income");

            transactionService.sortMapAndAddSortedElementsToSortedMap(
                    categoriesRankingIncomeMapSorted, categoriesRankingIncomeMap, Map.Entry.comparingByValue(Comparator.reverseOrder()));

            transactionService.findThreeCategoriesWithBiggestOrLowestValue(
                    categoriesRankingIncomeMapSorted, categoriesRanking, totalIncome, "income");
        }

        // Calculating which three categories were the least profitable.

        else {
            Map<String, Long> categoriesRankingOutcomeMapSorted = new LinkedHashMap<>();
            long totalOutcome = transactionService.getTotalTransaction(transactions, "outcome");

            Map<String, Long> categoriesRankingOutcomeMap = transactionService.getCategoriesRankingMap(transactions, "outcome");

            transactionService.sortMapAndAddSortedElementsToSortedMap(
                    categoriesRankingOutcomeMapSorted, categoriesRankingOutcomeMap, Map.Entry.comparingByValue());

            transactionService.findThreeCategoriesWithBiggestOrLowestValue(
                    categoriesRankingOutcomeMapSorted, categoriesRanking, totalOutcome, "outcome");
        }
        return categoriesRanking;
    }


}
