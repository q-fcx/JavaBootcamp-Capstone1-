package org.example.capstone1_ecommerce.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Product;
import org.example.capstone1_ecommerce.model.User;
import org.example.capstone1_ecommerce.service.DiscountService;
import org.example.capstone1_ecommerce.service.ProductService;
import org.example.capstone1_ecommerce.service.UserService;
import org.example.capstone1_ecommerce.service.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DiscountService discountService;
    private final WishListService wishListService;

    @GetMapping("/get")
    public ResponseEntity getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isAdded = userService.addUser(user);
        if (isAdded) {
            return ResponseEntity.accepted().body("User Added successfully");
        }
        return ResponseEntity.badRequest().body("Duplicate Id");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(message);
        }
        boolean isUpdated = userService.updateUser(id, user);
        if (isUpdated) {
            return ResponseEntity.accepted().body("User updated");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.accepted().body("User deleted Successfully");
        }
        return ResponseEntity.badRequest().body("Id not found");
    }

    @PostMapping("/buy/{userId}/{productId}/{merchantId}/{amount}")
    public ResponseEntity buyProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId, @PathVariable int amount) {
        String result = userService.buyProduct(userId, productId, merchantId, amount);

        if (result.equals("Purchase successful!")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("/recommendation/{userId}")
    public ResponseEntity getRecommendations(@PathVariable String userId) {
        ArrayList<Product> recommendedProduct = userService.getRecommendations(userId);
        if (recommendedProduct.isEmpty()) {
            return ResponseEntity.status(400).body("No recommendation yet");
        }
        return ResponseEntity.status(200).body(recommendedProduct);
    }


    @GetMapping("/top/{userId}")
    public ResponseEntity getTopCustomer(@PathVariable String userId) {
        String result = userService.getTopCustomer(userId);

        if (result.startsWith("can't access")) {
            return ResponseEntity.status(400).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/loyaltyDiscount/{userId}/{totalPrice}")
    public ResponseEntity customerLoyaltyDiscount(@PathVariable String userId, @PathVariable double totalPrice) {
        discountService.userLoyaltyDiscount(userId, totalPrice);
        return ResponseEntity.status(200).body("Loyalty Discount Applied Successfully");
    }

    @PostMapping("/addWish/{userId}/{productId}")
    public ResponseEntity addToWishlist(@PathVariable String userId, @PathVariable String productId) {
        String result = wishListService.addToWishlist(userId, productId);
        if (result.startsWith("Error")) {
            return ResponseEntity.status(400).body(result);
        }
        return ResponseEntity.status(200).body(result);
    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity removeFromWishlist(@PathVariable String userId, @PathVariable String productId) {
        String result = wishListService.removeFromWishlist(userId, productId);
        if (result.startsWith("Error")) {
            return ResponseEntity.status(400).body(result);
        }
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/view/{userId}")
    public ResponseEntity viewWishlist(@PathVariable String userId) {
        User user = userService.userSearchById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        ArrayList<String> wishlist = user.getWishlist();
        if (wishlist.isEmpty()) {
            return ResponseEntity.status(400).body("List is empty");
        }
        return ResponseEntity.status(200).body(wishlist);
    }
}
