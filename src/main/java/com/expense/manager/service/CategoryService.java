package com.expense.manager.service;

import com.expense.manager.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    void delete(Long id);

    Page<Category> findAllCategoriesByUsernamePageable(String username, Pageable pageable);
}
