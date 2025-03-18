package org.example.capstone1_ecommerce.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Product;
import org.example.capstone1_ecommerce.model.User;
import org.example.capstone1_ecommerce.service.ProductService;
import org.example.capstone1_ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isAdded = userService.addUser(user);
        if(isAdded) {
            return ResponseEntity.accepted().body("User Added successfully");
        }
        return ResponseEntity.badRequest().body("Duplicate Id");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        if(errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isUpdated = userService.updateUser(id, user);
        if(isUpdated) {
            return ResponseEntity.accepted().body("User updated");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        boolean isDeleted = userService.deleteUser(id);
        if(isDeleted) {
            return ResponseEntity.accepted().body("User deleted Successfully");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }

    @PostMapping("buy/{userId}/{productId}/{merchantId}/{amount}")
    public ResponseEntity buyProduct(@PathVariable String userId,@PathVariable String productId, @PathVariable String merchantId,  @PathVariable int amount) {
        boolean isPurchased = userService.buyProduct(userId, productId, merchantId, amount);
        if(isPurchased){
            return ResponseEntity.ok().body("Product purchased successfully");
        }

        return ResponseEntity.badRequest().body("Invalid Id: Check user Id, product id, or merchant id");
    }

    @GetMapping("/recommendation/{userId}")
    public ResponseEntity getRecommendations(@PathVariable String userId) {
        ArrayList<Product> recommendedProduct = userService.getRecommendations(userId);
        if(recommendedProduct.isEmpty()){
            return ResponseEntity.status(400).body("No recommendation yet");
        }
        return ResponseEntity.status(200).body(recommendedProduct);
    }

    @GetMapping("/top")
    public ResponseEntity getTopCustomer() {
        User topCustomer = userService.getTopCustomers();
        if(topCustomer == null) {
            return ResponseEntity.status(400).body("No customer added");
        }
        return ResponseEntity.status(200).body(topCustomer);
    }
}
