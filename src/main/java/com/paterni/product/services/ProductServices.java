package com.paterni.product.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.paterni.product.dto.ProductRequest;
import com.paterni.product.dto.ProductResponse;
import com.paterni.product.models.Category;
import com.paterni.product.models.Product;
import com.paterni.product.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse save(ProductRequest productRequest) {
        try {
            Product newProduct = productRepository.save(productRequest.toEntity());
            return newProduct.toDTO();
        } catch (DataIntegrityViolationException e) {
            throw new EntityNotFoundException("Category not found");
        }
    }

    public ProductResponse getById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException( "Product not found"));
        return product.toDTO();
    }

    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(p -> p.toDTO())
                .collect(Collectors.toList());
    }

    public void deteleById(long id) {
        if (productRepository.existsById(id))
            productRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Product not found");

    }

    public void update(long id, ProductRequest productUpdate) {
        try {
            Product product = productRepository.getReferenceById(id);

            Category category = new Category(productUpdate.getCategory().getId());
            product.setDescription(productUpdate.getDescription());
            product.setName(productUpdate.getName());
            product.setPrice(productUpdate.getPrice());
            product.setNewProduct(productUpdate.isNewProduct());
            product.setPromotion(productUpdate.isPromotion());
            product.setCategory(category);

            productRepository.save(product);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Product not found");
        } catch (DataIntegrityViolationException e) {
            throw new EntityNotFoundException("Category not found");
        }
    }

}