package com.expense.manager.service;

import com.expense.manager.model.Category;
import com.expense.manager.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<Category> findAllCategoriesByUsernamePageable(String username, Pageable pageable) {
        return categoryRepository.findAllByUserUsername(username, pageable);
    }
}
