package com.expense.manager.service;

import com.expense.manager.model.Transaction;
import com.expense.manager.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findAllTransactionsByUsernameBetweenTwoDates(String username, LocalDate fromDate, LocalDate toDate) {
        return transactionRepository.findAllByUserUsernameAndDateBetween(username,fromDate,toDate);
    }

    @Override
    public List<Transaction> findAllByUserUsernameOrderByDateDesc(String username) {
        return transactionRepository.findAllByUserUsernameOrderByDateDesc(username);
    }

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public Page<Transaction> findAllByUserUsernameOrderByDateDescPageable(String username, Pageable pageable) {
        return transactionRepository.findAllByUserUsernameOrderByDateDesc(username,pageable);
    }

    @Override
    public Page<Transaction> findAllByUserUsernameAndDateBetweenPageable(String username, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        return transactionRepository.findAllByUserUsernameAndDateBetween(username,fromDate,toDate,pageable);
    }

    @Override
    public Page<Transaction> findAllByUserUsernameAndDateBetweenAndTypeEquals(String username, LocalDate fromDate, LocalDate toDate, String type, Pageable pageable) {
        return transactionRepository.findAllByUserUsernameAndDateBetweenAndTypeEquals(username,fromDate,toDate,type,pageable);
    }
}
