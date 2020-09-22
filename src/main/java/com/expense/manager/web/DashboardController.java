package com.expense.manager.web;

import com.expense.manager.Pojo.CategoryRanking;
import com.expense.manager.model.Transaction;
import com.expense.manager.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.text.DecimalFormat;
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


        // Finds All transactions done by the user in last month.


        String username = principal.getName();
        LocalDate localDateNow = LocalDate.now();
        LocalDate localDateBefore = localDateNow.minusMonths(1);
        List<Transaction> transactions = transactionService.findAllTransactionsByUsernameBetweenTwoDates(username, localDateBefore, localDateNow);


        // Calculating last month balance / income / outcome


        long lastMonthBalance = transactions
                .stream().mapToLong(Transaction::getAmount).sum();
        long lastMonthIncome = transactions.stream()
                .filter(transaction -> transaction.getType().equals("income")).mapToLong(Transaction::getAmount).sum();
        long lastMonthOutcome = transactions.stream()
                .filter(transaction -> transaction.getType().equals("outcome")).mapToLong(Transaction::getAmount).sum();


        // Calculating which three categories were the most profitable in last month.


        List<CategoryRanking> categoriesIncomeRanking = new ArrayList<>();
        Map<String,Long> categoriesRankingIncomeMapSorted = new LinkedHashMap<>();

        Map<String,Long> categoriesRankingIncomeMap = transactions.stream()
                .filter(transaction -> transaction.getType().equals("income"))
                .collect(Collectors.toMap(Transaction::getCategory,Transaction::getAmount, Long::sum));

        categoriesRankingIncomeMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> categoriesRankingIncomeMapSorted.put(x.getKey(), x.getValue()));

        getThreeCategoriesWithBiggestOrLowestValue(categoriesRankingIncomeMapSorted, categoriesIncomeRanking,lastMonthIncome,"income");


        // Calculating which three categories were the least profitable in last month.


        Map<String,Long> categoriesRankingOutcomeMapSorted = new LinkedHashMap<>();
        List<CategoryRanking> categoriesOutcomeRanking = new ArrayList<>();

        Map<String,Long> categoriesRankingOutcomeMap = transactions.stream()
                .filter(transaction -> transaction.getType().equals("outcome"))
                .collect(Collectors.toMap(Transaction::getCategory,Transaction::getAmount, Long::sum));

        categoriesRankingOutcomeMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> categoriesRankingOutcomeMapSorted.put(x.getKey(), x.getValue()));

        getThreeCategoriesWithBiggestOrLowestValue(categoriesRankingOutcomeMapSorted, categoriesOutcomeRanking,lastMonthOutcome,"outcome");


        // Model attributes


        model.addAttribute("categoriesIncomeRanking",categoriesIncomeRanking);
        model.addAttribute("categoriesOutcomeRanking",categoriesOutcomeRanking);
        model.addAttribute("transactions",transactions);
        model.addAttribute("lastMonthBalance", lastMonthBalance);
        model.addAttribute("lastMonthIncome",lastMonthIncome);
        model.addAttribute("lastMonthOutcome",lastMonthOutcome);
        return "Dashboard";
    }

    public static void getThreeCategoriesWithBiggestOrLowestValue(Map<String, Long> categoriesRankingIncomeMap, List<CategoryRanking> categoriesIncomeRanking, long lastMonthIncomeOrOutcome, String type) {
        for(Map.Entry<String,Long> ranking : categoriesRankingIncomeMap.entrySet()) {
            if(categoriesIncomeRanking.size() < 3) {
                CategoryRanking categoryRanking = new CategoryRanking();
                categoryRanking.setName(ranking.getKey());
                categoryRanking.setAmount(ranking.getValue());
                double percent;
                if(type.equals("outcome"))
                    percent = (-(double)categoryRanking.getAmount() / -(double)lastMonthIncomeOrOutcome) * 100f;
                else
                    percent = ((double)categoryRanking.getAmount() / (double)lastMonthIncomeOrOutcome) * 100f;
                DecimalFormat decimalFormat = new DecimalFormat("###.##");
                decimalFormat.format(percent);
                categoryRanking.setPercentageContribution(percent);
                categoriesIncomeRanking.add(categoryRanking);
            }
        }
    }

}
