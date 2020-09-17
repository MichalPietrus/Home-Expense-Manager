package com.expense.manager.service;

import com.expense.manager.model.Transaction;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    void save(Transaction transaction);

    List<Transaction> findAllTransactionsByUsernameBetweenTwoDates(String username, LocalDate fromDate, LocalDate toDate);

    List<Transaction> findAllByUserUsernameOrderByDateDesc(String username);

    Transaction findById(Long id);

    void deleteById(Long id);

    List<Transaction> findAllByUserUsernameOrderByDateDescPageable(String username, Pageable pageable);

    List<Transaction> findAllByUserUsernameAndDateBetweenPageable(String username, LocalDate fromDate, LocalDate toDate, Pageable pageable);

}
