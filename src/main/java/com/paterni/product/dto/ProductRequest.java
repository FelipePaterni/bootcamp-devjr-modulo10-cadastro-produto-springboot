package com.paterni.product.dto;

import com.paterni.product.models.Category;
import com.paterni.product.models.Product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductRequest {

    @NotBlank(message = "Name can not be null")
    @Size(min = 3, max = 255, message = "Name length min = 3 and max = 255")
    private String name;

    @NotBlank(message = "Description can not be null")
    @Size(min = 3, max = 1020, message = "Description length min = 3 and max = 1024")
    private String description;

    @Min(value = 0, message = "Price min value = 0")
    private double price;

    private boolean promotion;
    private boolean newProduct;

    @NotNull
    @Valid
    private IntegerDTO category;

    public IntegerDTO getCategory() {
        return category;
    }

    public void setCategory(IntegerDTO category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    public boolean isNewProduct() {
        return newProduct;
    }

    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }

    public Product toEntity() {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setPromotion(promotion);
        product.setNewProduct(newProduct);

        product.setCategory(new Category(category.getId()));

        return product; 
    }

}
