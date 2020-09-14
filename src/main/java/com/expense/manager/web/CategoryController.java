package com.expense.manager.web;

import com.expense.manager.model.Category;
import com.expense.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final UserService userService;

    public CategoryController(@Autowired UserService userService) {
        this.userService = userService;
    }

    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "add-category";
    }

}
