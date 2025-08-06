package com.paterni.product.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.paterni.product.dto.CategoryRequest;
import com.paterni.product.dto.CategoryResponse;
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

    public CategoryResponse getDTOById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        return category.toDTO();
    }

    public List<CategoryResponse> getAll() {

        return categoryRepository.findAll()
                .stream()
                .map(c -> c.toDTO())
                .collect(Collectors.toList());
    }

    public void deteleById(int id) {
        Category category = getById(id);
        categoryRepository.delete(category);
    }

    public void update(int id, CategoryRequest categoryUpdate) {
        Category category = getById(id);

        category.setName(categoryUpdate.getName());

        categoryRepository.save(category);
    }

    public CategoryResponse save(CategoryRequest categoryRequest) {
        Category category = categoryRepository.save(categoryRequest.toEntity());
        return category.toDTO();
    }

}