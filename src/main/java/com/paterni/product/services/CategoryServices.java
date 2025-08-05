package com.paterni.product.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.paterni.product.models.Category;
import com.paterni.product.repositories.CategoryRepository;

import org.springframework.stereotype.Service;

@Service
public class CategoryServices {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category getById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        return category;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public void deteleById(int id) {
        Category category = getById(id);
        categoryRepository.delete(category);
    }

    public void update(int id, Category categoryUpdate) {
        Category category = getById(id);

        category.setName(categoryUpdate.getName());

        categoryRepository.save(category);
    }

    
    public Category save(Category product) {
        return categoryRepository.save(product);
    }

}