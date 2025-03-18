package org.example.capstone1_ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Product;
import org.example.capstone1_ecommerce.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final ProductService productService;
    private final UserService userService;
    // Method to apply category-based discount
    public double categoryDiscount(String productId) {
        double discount = 0.0;

        Product product = productService.productSearchById(productId);
            if (product.getCategoryId().equals("electronics")) {
                discount = 0.20;
            }
            if (product.getCategoryId().equals("books")) {
                discount = 0.10;
            }

            return product.getPrice() * discount;
        }


    public double userLoyaltyDiscount(String userId, double totalPrice) {
        double discount = 0.0;

        // Find the user by userId
        User user = userService.userSearchById(userId);
        if (user == null) {
            return 0.0;
        }
        if (user.getTotalSpending() > 1000 && user.getTotalSpending() <= 5000) {
            discount = 0.05;
        }
        if (user.getTotalSpending() > 5000) {
            discount = 0.10;
        }

        return totalPrice * discount;
    }

    public double calculateTotalDiscount(String userId, String productId, double totalPrice) {
        double loyaltyDiscount = userLoyaltyDiscount(userId, totalPrice);
        double categoryDiscount = categoryDiscount(productId);

        double totalDiscount = loyaltyDiscount + categoryDiscount;

        return totalDiscount;
    }
}
