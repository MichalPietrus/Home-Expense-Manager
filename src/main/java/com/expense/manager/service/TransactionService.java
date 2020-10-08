package com.expense.manager.service;

import com.expense.manager.Pojo.CategoryRanking;
import com.expense.manager.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface TransactionService {

    void save(Transaction transaction);

    List<Transaction> findAllTransactionsByUsernameBetweenTwoDates(String username, LocalDate fromDate, LocalDate toDate);

    List<Transaction> findAllByUserUsernameOrderByDateDesc(String username);

    Transaction findById(Long id);

    void deleteById(Long id);

    Page<Transaction> findAllByUserUsernameOrderByDateDescPageable(String username, Pageable pageable);

    Page<Transaction> findAllByUserUsernameAndDateBetweenPageable(
            String username,
            LocalDate fromDate, LocalDate toDate,
            Pageable pageable);

    Page<Transaction> findAllByUserUsernameAndDateBetweenAndTypeEquals(
            String username,
            LocalDate fromDate, LocalDate toDate, String type,
            Pageable pageable);

    void findThreeCategoriesWithBiggestOrLowestValue(
            Map<String, Long> categoriesRankingIncomeMap,
            List<CategoryRanking> categoriesIncomeRanking,
            long lastMonthIncomeOrOutcome, String type);


    void sortMapAndAddSortedElementsToSortedMap(Map<String, Long> categoriesRankingIncomeMapSorted,
                                                Map<String, Long> categoriesRankingIncomeMap,
                                                Comparator<Map.Entry<String, Long>> entryComparator);

    Map<String, Long> getCategoriesRankingMap(List<Transaction> transactions, String income);

    long getTotalTransaction(List<Transaction> transactions, String transactionType);
}
