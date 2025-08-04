package com.paterni.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paterni.product.models.Product;

public interface ProductRepository extends JpaRepository<Product,Integer>{

}
