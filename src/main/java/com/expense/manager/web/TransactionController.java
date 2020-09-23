package com.expense.manager.web;

import com.expense.manager.model.Category;
import com.expense.manager.model.Transaction;
import com.expense.manager.model.User;
import com.expense.manager.service.TransactionService;
import com.expense.manager.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private final UserService userService;

    private final TransactionService transactionService;

    public TransactionController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("add/{type}")
    public String showTransactionForm(Model model,
                                      @PathVariable String type, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Category> categories = user.getCategories().stream().filter(category -> category.getType().equals(type)).collect(toList());
        model.addAttribute("categories", categories);
        model.addAttribute("type", type);
        model.addAttribute("transaction", new Transaction());
        return "add-transaction";
    }

    @PostMapping("add/{type}")
    public String saveTransaction(@ModelAttribute("transaction") @Valid Transaction transaction,
                                  @PathVariable String type, Principal principal) throws Exception {
        User user = userService.findByUsername(principal.getName());
        if (type.equals("outcome") && transaction.getAmount() > 0)
            transaction.setAmount(-transaction.getAmount());
        else if (type.equals("outcome") && transaction.getAmount() < 0)
            transaction.setAmount(transaction.getAmount());
        else if (!type.equals("income"))
            throw new Exception("Wrong type of transaction");
        transaction.setType(type);
        user.addTransaction(transaction);
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/history/{pageId}")
    public String showTransactionsHistory(Model model, @PathVariable Integer pageId, Principal principal) {
        Pageable pageable = PageRequest.of(pageId, 5, Sort.by(Sort.Order.asc("date")));
        Page<Transaction> transactions = transactionService.findAllByUserUsernameOrderByDateDescPageable(principal.getName(), pageable);
        LocalDate localDate = LocalDate.now().plusDays(1);
        LocalDate localDateMonthBefore = LocalDate.now().minusMonths(1);
        long lastMonthBalance = transactionService.findAllByUserUsernameOrderByDateDesc(principal.getName()).stream()
                .filter(transaction -> transaction.getDate().isAfter(localDateMonthBefore) && transaction.getDate().isBefore(localDate))
                .mapToLong(Transaction::getAmount).sum();
        model.addAttribute("lastMonthBalance", lastMonthBalance);
        model.addAttribute("transactions", transactions);
        return "transactions-history";
    }

    @GetMapping("edit/{transactionId}")
    public String showEditTransactionFrom(Model model, @PathVariable Long transactionId, Principal principal) {
        Transaction transaction = transactionService.findById(transactionId);
        //Finds all categories by type and logged in user
        List<String> categories = userService.findByUsername(principal.getName()).getCategories().stream()
                .filter(category -> category.getType().equals(transaction.getType()))
                .filter(category -> !category.getName().equals(transaction.getCategory()))
                .map(Category::getName).collect(Collectors.toList());
        model.addAttribute("transaction", transaction);
        model.addAttribute("categories", categories);
        return "edit-transaction";
    }

    @PostMapping("edit/{transactionId}")
    public String editTransaction(@ModelAttribute("transaction") Transaction transaction,
                                  @PathVariable Long transactionId, Principal principal) {
        long transactionValue = transaction.getAmount();
        if (transactionValue < 0 && transaction.getType().equals("income"))
            transactionValue = transactionValue - transactionValue * 2;
        if (transactionValue > 0 && transaction.getType().equals("outcome"))
            transactionValue = transactionValue - transactionValue * 2;
        transaction.setAmount(transactionValue);
        transaction.setId(transactionId);
        transaction.setUser(userService.findByUsername(principal.getName()));
        transactionService.save(transaction);
        return "redirect:/transaction/history/0";
    }

    @GetMapping("delete/{transactionId}")
    public String deleteTransaction(@PathVariable("transactionId") Long transactionId) {
        transactionService.deleteById(transactionId);
        return "redirect:/transaction/history/0";
    }


}
