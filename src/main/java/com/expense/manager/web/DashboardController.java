package com.expense.manager.web;

import com.expense.manager.Pojo.CategoryRanking;
import com.expense.manager.model.Transaction;
import com.expense.manager.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/")
public class DashboardController {

    private final TransactionService transactionService;

    public DashboardController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public String showDashboard(Model model, Principal principal) {

        // Finds All transactions done by the user in last month.

        String username = principal.getName();
        LocalDate localDateNow = LocalDate.now();
        LocalDate localDateBefore = localDateNow.minusMonths(1);
        List<Transaction> transactions = transactionService
                .findAllTransactionsByUsernameBetweenTwoDates(username, localDateBefore, localDateNow);

        // Calculating last month balance / income / outcome

        long lastMonthBalance = transactions.stream()
                .mapToLong(Transaction::getAmount).sum();
        long lastMonthIncome = transactionService.getTotalTransaction(transactions, "income");
        long lastMonthOutcome = transactionService.getTotalTransaction(transactions, "outcome");

        // Calculating which three categories were the most profitable in last month.

        List<CategoryRanking> categoriesIncomeRanking = new ArrayList<>();
        Map<String, Long> categoriesRankingIncomeMapSorted = new LinkedHashMap<>();
        Map<String, Long> categoriesRankingIncomeMap = transactionService.getCategoriesRankingMap(transactions, "income");

        transactionService.sortMapAndAddSortedElementsToSortedMap(
                categoriesRankingIncomeMapSorted, categoriesRankingIncomeMap, Map.Entry.comparingByValue(Comparator.reverseOrder()));

        transactionService.findThreeCategoriesWithBiggestOrLowestValue
                (categoriesRankingIncomeMapSorted, categoriesIncomeRanking, lastMonthIncome, "income");

        // Calculating which three categories were the least profitable in last month.

        Map<String, Long> categoriesRankingOutcomeMapSorted = new LinkedHashMap<>();
        List<CategoryRanking> categoriesOutcomeRanking = new ArrayList<>();
        Map<String, Long> categoriesRankingOutcomeMap = transactionService.getCategoriesRankingMap(transactions, "outcome");

        transactionService.sortMapAndAddSortedElementsToSortedMap(
                categoriesRankingOutcomeMapSorted, categoriesRankingOutcomeMap, Map.Entry.comparingByValue());

        transactionService.findThreeCategoriesWithBiggestOrLowestValue
                (categoriesRankingOutcomeMapSorted, categoriesOutcomeRanking, lastMonthOutcome, "outcome");

        // Model attributes

        model.addAttribute("categoriesIncomeRanking", categoriesIncomeRanking);
        model.addAttribute("categoriesOutcomeRanking", categoriesOutcomeRanking);
        model.addAttribute("transactions", transactions);
        model.addAttribute("lastMonthBalance", lastMonthBalance);
        model.addAttribute("lastMonthIncome", lastMonthIncome);
        model.addAttribute("lastMonthOutcome", lastMonthOutcome);
        return "Dashboard";
    }

}
