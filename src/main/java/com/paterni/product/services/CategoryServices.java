package com.paterni.product.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.paterni.product.dto.CategoryRequest;
import com.paterni.product.dto.CategoryResponse;
import com.paterni.product.models.Category;
import com.paterni.product.repositories.CategoryRepository;
import com.paterni.product.services.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class CategoryServices {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponse save(CategoryRequest categoryRequest) {
        try {
            Category category = categoryRepository.save(categoryRequest.toEntity());
            return category.toDTO();
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation, category name already exists");
        }

    }

    public CategoryResponse getById(int id) {
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
        try {
            if (categoryRepository.existsById(id)) {
                categoryRepository.deleteById(id);
            } else
                throw new EntityNotFoundException("Category not found");

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Constrain violation, category can't delete");
        }

    }

    public void update(int id, CategoryRequest categoryUpdate) {
        try {
            Category category = categoryRepository.getReferenceById(id);
            category.setName(categoryUpdate.getName());
            categoryRepository.save(category);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Category not found");
        }
    }

}