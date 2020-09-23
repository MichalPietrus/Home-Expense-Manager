package com.expense.manager.service;

import com.expense.manager.Pojo.CategoryRanking;
import com.expense.manager.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionService {

    void save(Transaction transaction);

    List<Transaction> findAllTransactionsByUsernameBetweenTwoDates(String username, LocalDate fromDate, LocalDate toDate);

    List<Transaction> findAllByUserUsernameOrderByDateDesc(String username);

    Transaction findById(Long id);

    void deleteById(Long id);

    Page<Transaction> findAllByUserUsernameOrderByDateDescPageable(String username, Pageable pageable);

    Page<Transaction> findAllByUserUsernameAndDateBetweenPageable(String username, LocalDate fromDate, LocalDate toDate, Pageable pageable);

    Page<Transaction> findAllByUserUsernameAndDateBetweenAndTypeEquals(String username, LocalDate fromDate, LocalDate toDate, String type, Pageable pageable);

    void getThreeCategoriesWithBiggestOrLowestValue
            (Map<String, Long> categoriesRankingIncomeMap,
             List<CategoryRanking> categoriesIncomeRanking, long lastMonthIncomeOrOutcome, String type);
}
