package com.paterni.product.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.paterni.product.dto.ProductRequest;
import com.paterni.product.dto.ProductResponse;
import com.paterni.product.models.Category;
import com.paterni.product.models.Product;
import com.paterni.product.repositories.ProductRepository;

@Service
public class ProductServices {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryServices categoryServices;

    public ProductResponse save(ProductRequest productRequest) {
        Product newProduct = productRepository.save(productRequest.toEntity());
     
        return newProduct.toDTO();
    }

     public ProductResponse getDTOById(long id) {
        Product product = getById(id);
        return product.toDTO();
    }


    public Product getById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return product;
    }

    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                                .stream()
                                .map(p -> p.toDTO())
                                .collect(Collectors.toList());
    }

    public void deteleById(long id) {
        Product product = getById(id);
        productRepository.delete(product);
    }

    public void update(long id , ProductRequest productUpdate) {
    Product product = getById(id);

        checkCategory(productUpdate.toEntity());
        
        Category category = categoryServices.getById(productUpdate.toEntity().getCategory().getId());
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

  
}