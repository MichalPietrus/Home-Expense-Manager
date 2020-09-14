package com.expense.manager.web;

import com.expense.manager.model.Category;
import com.expense.manager.model.Transaction;
import com.expense.manager.model.User;
import com.expense.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private final UserService userService;

    public TransactionController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{type}")
    public String showTransactionForm(Model model,
                                      @PathVariable String type, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Category> categories = user.getCategories().stream().filter(category -> category.getType().equals(type)).collect(Collectors.toList());
        model.addAttribute("categories",categories);
        model.addAttribute("type",type);
        model.addAttribute("transaction",new Transaction());
        return "add-transaction";
    }

    @PostMapping("{type}")
    public String saveTransaction(@ModelAttribute("transaction") @Valid Transaction transaction, BindingResult transactionResult,
                                  @PathVariable String type, RedirectAttributes redirectAttributes,
                                  Principal principal) throws Exception {
        User user = userService.findByUsername(principal.getName());
        if(transactionResult.hasErrors()) {
            redirectAttributes.addAttribute("type",type);
            return "redirect:/transaction/{type}";
        }
        if (type.equals("outcome"))
            transaction.setAmount(-transaction.getAmount());
        else if (!type.equals("income"))
            throw new Exception("Wrong type of transaction");
        transaction.setType(type);
        user.addTransaction(transaction);
        userService.save(user);
        return "redirect:/";

    }

}
