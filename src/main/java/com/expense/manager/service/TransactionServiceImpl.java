package com.expense.manager.service;

import com.expense.manager.Pojo.CategoryRanking;
import com.expense.manager.model.Transaction;
import com.expense.manager.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<Transaction> findAllTransactionsByUsernameBetweenTwoDates(
            String username,
            LocalDate fromDate, LocalDate toDate) {
        return transactionRepository.findAllByUserUsernameAndDateBetween(username, fromDate, toDate);
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
        return transactionRepository.findAllByUserUsernameOrderByDateDesc(username, pageable);
    }

    @Override
    public Page<Transaction> findAllByUserUsernameAndDateBetweenPageable(
            String username,
            LocalDate fromDate, LocalDate toDate,
            Pageable pageable) {
        return transactionRepository
                .findAllByUserUsernameAndDateBetween(username, fromDate, toDate, pageable);
    }

    @Override
    public Page<Transaction> findAllByUserUsernameAndDateBetweenAndTypeEquals(
            String username,
            LocalDate fromDate, LocalDate toDate,
            String type, Pageable pageable) {
        return transactionRepository
                .findAllByUserUsernameAndDateBetweenAndTypeEquals(username, fromDate, toDate, type, pageable);
    }

    @Override
    public void findThreeCategoriesWithBiggestOrLowestValue(
            Map<String, Long> categoriesRankingIncomeMap,
            List<CategoryRanking> categoriesIncomeRanking,
            long lastMonthIncomeOrOutcome, String type) {

        for (Map.Entry<String, Long> ranking : categoriesRankingIncomeMap.entrySet()) {
            if (categoriesIncomeRanking.size() < 3) {
                CategoryRanking categoryRanking = new CategoryRanking();
                categoryRanking.setName(ranking.getKey());
                categoryRanking.setAmount(ranking.getValue());
                double percent;
                if (type.equals("outcome"))
                    percent = (-(double) categoryRanking.getAmount() / -(double) lastMonthIncomeOrOutcome) * 100f;
                else
                    percent = ((double) categoryRanking.getAmount() / (double) lastMonthIncomeOrOutcome) * 100f;
                DecimalFormat decimalFormat = new DecimalFormat("###.##");
                decimalFormat.format(percent);
                categoryRanking.setPercentageContribution(percent);
                categoriesIncomeRanking.add(categoryRanking);
            }
        }
    }

    @Override
    public void sortMapAndAddSortedElementsToSortedMap(Map<String, Long> categoriesRankingIncomeMapSorted,
                                                       Map<String, Long> categoriesRankingIncomeMap,
                                                       Comparator<Map.Entry<String, Long>> entryComparator) {
        categoriesRankingIncomeMap.entrySet()
                .stream()
                .sorted(entryComparator)
                .forEachOrdered(x -> categoriesRankingIncomeMapSorted.put(x.getKey(), x.getValue()));
    }

    @Override
    public Map<String, Long> getCategoriesRankingMap(List<Transaction> transactions, String income) {
        return transactions.stream()
                .filter(transaction -> transaction.getType().equals(income))
                .collect(Collectors.toMap(Transaction::getCategory, Transaction::getAmount, Long::sum));
    }

    @Override
    public long getTotalTransaction(List<Transaction> transactions, String transactionType) {
        return transactions.stream()
                .filter(transaction -> transaction.getType().equals(transactionType))
                .mapToLong(Transaction::getAmount).sum();
    }
}
