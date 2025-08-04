package com.paterni.product.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.paterni.product.models.Category;
import com.paterni.product.models.Product;
import com.paterni.product.repositories.ProductRepository;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryServices categoryServices;

    public Product save(Product product) {
        checkCategory(product);
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

        checkCategory(productUpdate);
        
        Category category = categoryServices.getById(productUpdate.getCategory().getId());
        product.setDescription(productUpdate.getDescription());
        product.setName(productUpdate.getName());
        product.setPrice(productUpdate.getPrice());
        product.setNewProduct(productUpdate.isNewProduct());
        product.setPromotion(productUpdate.isPromotion());
        product.setCategory(category);

        productRepository.save(product);
    }

    public Category checkCategory(Product product){
        if (product.getCategory() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category can not be empty");
        }
       return categoryServices.getById(product.getCategory().getId());
    }

    public Category getCategoryByProductID(int id){
        return  getById(id).getCategory();
    }
}