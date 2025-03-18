package org.example.capstone1_ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Category;
import org.example.capstone1_ecommerce.model.Product;
import org.example.capstone1_ecommerce.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryService categoryService;

    ArrayList<Product> products = new ArrayList<>();

    public Product productSearchById(String id) {
        for(Product product : products) {
            if(product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean addProduct(Product product) {
        for (Product c : products) {
            if (c.getId().equals(product.getId())) {
                return false;
            }
        }
        for (Category c : categoryService.categories) {
            if (!c.getId().equals(product.getCategoryId())) {
                products.add(product);
                return false;
            }
        }
        products.add(product);
        return true;
    }

    public boolean updateProduct(String id, Product product) {
        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).getId().equals(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id) {
        for(Product c : products) {
            if(c.getId().equals(id)) {
                products.remove(c);
                return true;
            }
        }
        return false;
    }

}

