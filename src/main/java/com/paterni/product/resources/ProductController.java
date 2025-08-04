package com.paterni.product.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.paterni.product.models.Category;
import com.paterni.product.models.Product;
import com.paterni.product.services.ProductServices;


@RestController
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @GetMapping("products")
    public List<Product> getProducts() {
        return productServices.getAll();
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        return ResponseEntity.ok(productServices.getById(id));
    }

    @GetMapping("products/{id}/category")
    public ResponseEntity<Category> getCategory(@PathVariable int id) {
        Category category = productServices.getCategoryByProductID(id);
        return ResponseEntity.ok(category);
    }
    

    @DeleteMapping("products/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable int id) {
        productServices.deteleById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable int id, @RequestBody Product productUpdate) {
        productServices.update(id, productUpdate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("products")
    public ResponseEntity<Product> save(@RequestBody Product product) {

        product = productServices.save(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(location).body(product);
    }
}