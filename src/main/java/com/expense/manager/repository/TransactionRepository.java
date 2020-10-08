package com.expense.manager.repository;

import com.expense.manager.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByUserUsernameAndDateBetween(String username, LocalDate fromDate, LocalDate toDate);

    List<Transaction> findAllByUserUsernameOrderByDateDesc(String username);

    Page<Transaction> findAllByUserUsernameOrderByDateDesc(String username, Pageable pageable);

    Page<Transaction> findAllByUserUsernameAndDateBetween(String username, LocalDate fromDate, LocalDate toDate, Pageable pageable);

    Page<Transaction> findAllByUserUsernameAndDateBetweenAndTypeEquals(
            String username,
            LocalDate fromDate, LocalDate toDate,
            String type, Pageable pageable);
}
