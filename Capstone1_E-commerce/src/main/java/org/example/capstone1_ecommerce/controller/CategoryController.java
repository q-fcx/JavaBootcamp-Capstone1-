package org.example.capstone1_ecommerce.controller;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Category;
import org.example.capstone1_ecommerce.model.Product;
import org.example.capstone1_ecommerce.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isAdded = categoryService.addCategory(category);
        if(isAdded) {
            return ResponseEntity.accepted().body("Category Added successfully");
        }
        return ResponseEntity.badRequest().body("Duplicate Id");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isUpdated = categoryService.updateCategory(id, category);
        if(isUpdated) {
            return ResponseEntity.accepted().body("Category updated");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id) {
        boolean isDeleted = categoryService.deleteCategory(id);
        if(isDeleted) {
            return ResponseEntity.accepted().body("Category deleted Successfully");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }


}
