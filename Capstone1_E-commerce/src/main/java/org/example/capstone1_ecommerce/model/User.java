package org.example.capstone1_ecommerce.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty
    private String id;
    @NotEmpty
    //@Min(value = 5, message = "User name must be more than 5 characters")
    private String userName;
    @NotEmpty
    //@Min(value = 6, message = "Password must be more than 6")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must contain letters and digits")
    private String password;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Pattern(regexp = "admin|customer", message = "Role can either be admin or customer")
    private String role;
    @NotNull
    @Positive
    private double balance;

    private ArrayList<String> purchasedProducts = new ArrayList<>();
    private ArrayList<String> purchasedCategories = new ArrayList<>();
    private ArrayList<String> wishlist = new ArrayList<>();

    private double totalSpending;
}
