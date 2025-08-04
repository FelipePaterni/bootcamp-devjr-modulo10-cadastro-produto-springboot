package com.paterni.product.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.paterni.product.models.Category;
import com.paterni.product.models.Product;
import com.paterni.product.repositories.CategoryRepository;
import com.paterni.product.repositories.ProductRepository;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product getById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return product;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void deteleById(int id) {
        Product product = getById(id);
        productRepository.delete(product);
    }

    public void update(int id , Product productUpdate) {
    Product product = getById(id);

        if (productUpdate.getCategory() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category can not be empty");
        }
        Category category = categoryRepository.findById(productUpdate.getCategory().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        product.setDescription(productUpdate.getDescription());
        product.setName(productUpdate.getName());
        product.setPrice(productUpdate.getPrice());
        product.setNewProduct(productUpdate.isNewProduct());
        product.setPromotion(productUpdate.isPromotion());
        product.setCategory(category);

        productRepository.save(product);
    }
}
