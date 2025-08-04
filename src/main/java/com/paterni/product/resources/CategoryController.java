package com.paterni.product.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.paterni.product.models.Category;
import com.paterni.product.services.CategoryServices;

@RestController
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryServices categoryServices;

    @GetMapping("categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable int id) {
        return ResponseEntity.ok(categoryServices.getById(id));
    }

    @GetMapping("categories")
    public List<Category> getCategories() {
        return categoryServices.getAll();
    }

}
