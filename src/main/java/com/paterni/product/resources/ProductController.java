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

import com.paterni.product.dto.ProductRequest;
import com.paterni.product.dto.ProductResponse;
import com.paterni.product.services.ProductServices;

import jakarta.validation.Valid;


@RestController
@CrossOrigin
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return  ResponseEntity.ok(productServices.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(productServices.getDTOById(id));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable long id) {
        productServices.deteleById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable long id, @Valid @RequestBody ProductRequest productUpdate) {
        productServices.update(id, productUpdate);
        return ResponseEntity.ok().build();
    }

    @PostMapping()
    public ResponseEntity<ProductResponse> save(@Validated @RequestBody ProductRequest productRequest) {

       ProductResponse productResponse = productServices.save(productRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productResponse.getId())
                .toUri();
        return ResponseEntity.created(location).body(productResponse);
    }
}