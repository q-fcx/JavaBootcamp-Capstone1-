package org.example.capstone1_ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Merchant;
import org.example.capstone1_ecommerce.model.MerchantStock;
import org.example.capstone1_ecommerce.model.Product;
import org.example.capstone1_ecommerce.model.User;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;
    private final DiscountService discountService;

    ArrayList<User> users = new ArrayList<>();


    public ArrayList<User> getUsers() {
        return users;
    }

    public boolean addUser(User user) {
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                return false;
            }
        }
        users.add(user);
        return true;
    }

    public boolean updateUser(String id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                users.remove(u);
                return true;
            }
        }
        return false;
    }

    public User userSearchById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
    public ArrayList<Product> getRecommendations(String userId) {
        User user = userSearchById(userId);
        ArrayList<Product> recommendations = new ArrayList<>();

        if (user != null && user.getPurchasedCategories() != null) {
            for (Product product : productService.products) {
                if (user.getPurchasedCategories().contains(product.getCategoryId()) && !user.getPurchasedProducts().contains(product.getId())) {
                    recommendations.add(product);
                }
            }
        }
        return recommendations;

    }

    public boolean buyProduct(String userId, String productId, String merchantId, int amount) {
       User user =  userSearchById(userId);
        if (user == null) return false;

        Product product = productService.productSearchById(productId);
        if (product == null) return false;

        Merchant merchant = merchantService.merchantSearchById(merchantId);
        if (merchant == null) return false;

        MerchantStock stock = merchantStockService.findMerchantStock(productId, merchantId);
        if (stock == null) return false;

        if (stock.getStock() < amount) return false;

        double totalPrice = product.getPrice() * amount;

        double discount = discountService.calculateTotalDiscount(userId, productId, totalPrice);
        totalPrice -= discount;
        if (user.getBalance() < totalPrice) return false;

        user.setBalance(user.getBalance() - totalPrice);
        stock.setStock(stock.getStock() - amount);
        user.getPurchasedCategories().add(product.getCategoryId());
        user.getPurchasedProducts().add(productId);
        user.setTotalSpending(user.getTotalSpending() + totalPrice);


        return true;
    }



    public User getTopCustomers() {
        User topCustomer = users.get(0);
        double maxSpending = topCustomer.getTotalSpending();
        for(User user : users) {
            double totalSpending = user.getTotalSpending();
            if(totalSpending > maxSpending) {
                topCustomer = user;
                maxSpending = totalSpending;
            }
        }
        return topCustomer;
    }
}


