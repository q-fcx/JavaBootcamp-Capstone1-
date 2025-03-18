package org.example.capstone1_ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Category;
import org.example.capstone1_ecommerce.model.Product;
import org.example.capstone1_ecommerce.service.ProductService;
import org.example.capstone1_ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isAdded = productService.addProduct(product);
        if(isAdded) {
            return ResponseEntity.accepted().body("Product Added successfully");
        }
        return ResponseEntity.badRequest().body("Duplicate Id");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isUpdated = productService.updateProduct(id, product);
        if(isUpdated) {
            return ResponseEntity.accepted().body("Product updated");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        boolean isDeleted = productService.deleteProduct(id);
        if(isDeleted) {
            return ResponseEntity.accepted().body("Product deleted Successfully");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }
}