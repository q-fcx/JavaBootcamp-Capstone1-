package org.example.capstone1_ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Product;
import org.example.capstone1_ecommerce.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final UserService userService;
    private final ProductService productService;

    public String addToWishlist(String userId, String productId) {
        User user = userService.userSearchById(userId);
        if (user == null) return "Error: User not found";

        if (!"customer".equals(user.getRole())) {
            return "Error: Only customers can use wishlists";
        }

        Product product = productService.productSearchById(productId);
        if (product == null) return "Error: Product not found";

        if (user.getWishlist().contains(productId)) {
            return "Product already in wishlist";
        }

        user.getWishlist().add(productId);
        return "Product added to wishlist!";
    }

    public String removeFromWishlist(String userId, String productId) {
        User user = userService.userSearchById(userId);
        if (user == null) return "Error: User not found";

        if (!user.getWishlist().contains(productId)) {
            return "Error: Product not found in wishlist";
        }

        user.getWishlist().remove(productId);
        return "Product removed from wishlist";
    }
}
