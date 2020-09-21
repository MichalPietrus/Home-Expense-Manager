package com.expense.manager.web;

import com.expense.manager.model.Category;
import com.expense.manager.model.User;
import com.expense.manager.service.CategoryService;
import com.expense.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final UserService userService;
    private final CategoryService categoryService;

    public CategoryController(@Autowired UserService userService, @Autowired CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list/{pageId}")
    public String showCategories(Model model, Principal principal, @PathVariable Integer pageId) {
        Pageable pageable = PageRequest.of(pageId, 5, Sort.by(Sort.Order.asc("name")));
        Page<Category> categories = categoryService.findAllCategoriesByUsernamePageable(principal.getName(), pageable);
        model.addAttribute("categories",categories);
        return "category-list";
    }

    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "add-category";
    }

    @PostMapping("/add")
    public String saveCategory(@ModelAttribute("category") @Valid Category category,
                               Principal principal) {
        User user = userService.findByUsername(principal.getName());
        user.addCategory(category);
        userService.save(user);
        return "redirect:/category/list/0";
    }

    @GetMapping("/delete-category/{pageId}/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes, @PathVariable Integer pageId) {
        categoryService.delete(id);
        redirectAttributes.addAttribute("pageId",pageId);
        return "redirect:/category/list/{pageId}";
    }

}
