package com.paterni.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paterni.product.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
}
