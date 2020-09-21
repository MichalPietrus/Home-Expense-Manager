package com.expense.manager.web;

import com.expense.manager.MapUtil;
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
import java.util.stream.Collectors;

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
        List<Transaction> transactions = transactionService.findAllTransactionsByUsernameBetweenTwoDates(username, localDateBefore, localDateNow)
                .stream().sorted(Comparator.comparing(Transaction::getAmount).reversed()).collect(Collectors.toList());
        Map<String,Long> categoriesRankingIncomeMap = transactions.stream()
                .filter(transaction -> transaction.getType().equals("income")).collect(Collectors.toMap(Transaction::getCategory,Transaction::getAmount, Long::sum));
        MapUtil.sortByValue(categoriesRankingIncomeMap);
        Map<String,Long> categoriesRankingOutcomeMap = transactions.stream()
                .filter(transaction -> transaction.getType().equals("outcome")).collect(Collectors.toMap(Transaction::getCategory,Transaction::getAmount, Long::sum));
        MapUtil.sortByValue(categoriesRankingOutcomeMap);
        List<CategoryRanking> categoriesIncomeRanking = new ArrayList<>();
        List<CategoryRanking> categoriesOutcomeRanking = new ArrayList<>();
        getThreeCategoriesWithBiggestOrLowestValue(categoriesRankingIncomeMap, categoriesIncomeRanking);
        getThreeCategoriesWithBiggestOrLowestValue(categoriesRankingOutcomeMap, categoriesOutcomeRanking);
        long lastMonthBalance = transactions.stream().mapToLong(Transaction::getAmount).sum();
        long lastMonthIncome = transactions.stream().filter(transaction -> transaction.getType().equals("income")).mapToLong(Transaction::getAmount).sum();
        long lastMonthOutcome = transactions.stream().filter(transaction -> transaction.getType().equals("outcome")).mapToLong(Transaction::getAmount).sum();
        model.addAttribute("categoriesIncomeRanking",categoriesIncomeRanking);
        model.addAttribute("categoriesOutcomeRanking",categoriesOutcomeRanking);
        model.addAttribute("transactions",transactions);
        model.addAttribute("lastMonthBalance", lastMonthBalance);
        model.addAttribute("lastMonthIncome",lastMonthIncome);
        model.addAttribute("lastMonthOutcome",lastMonthOutcome);
        return "Dashboard";
    }

    private void getThreeCategoriesWithBiggestOrLowestValue(Map<String, Long> categoriesRankingIncomeMap, List<CategoryRanking> categoriesIncomeRanking) {
        for(Map.Entry<String,Long> ranking : categoriesRankingIncomeMap.entrySet()) {
            if(categoriesIncomeRanking.size() < 3) {
                CategoryRanking categoryRanking = new CategoryRanking();
                categoryRanking.setName(ranking.getKey());
                categoryRanking.setAmount(ranking.getValue());
                categoriesIncomeRanking.add(categoryRanking);
            }
        }
    }

}
