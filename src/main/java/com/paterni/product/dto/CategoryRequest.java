package com.paterni.product.dto;

import com.paterni.product.models.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryRequest {
    @NotBlank(message = "Name can not be null")
    @Size(min = 3, max = 255, message = "Name length min = 3 and max = 255")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category toEntity() {
        return new Category(name);
    }

}
