package com.paterni.product.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.paterni.product.dto.CategoryRequest;
import com.paterni.product.dto.CategoryResponse;
import com.paterni.product.services.CategoryServices;

@RestController
@CrossOrigin
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryServices categoryServices;

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable int id) {
        CategoryResponse category = categoryServices.getDTOById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> getCategories() {
            return ResponseEntity.ok(categoryServices.getAll());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeCategory(@PathVariable int id) {
        categoryServices.deteleById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable int id, @RequestBody CategoryRequest categoryUpdate) {
        categoryServices.update(id, categoryUpdate);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<CategoryResponse> save(@Validated @RequestBody CategoryRequest categoryRequest) {

        CategoryResponse category = categoryServices.save(categoryRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();
        return ResponseEntity.created(location).body(category);
    }
}
