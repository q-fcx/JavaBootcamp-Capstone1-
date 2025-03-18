package org.example.capstone1_ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1_ecommerce.model.Category;
import org.example.capstone1_ecommerce.model.Product;
import org.example.capstone1_ecommerce.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CategoryService {

    ArrayList<Category> categories = new ArrayList<>();

    public Category categorySearchById(String id) {
        for (Category category : categories) {
            if (category.getId().equals(id)) {
                return category;
            }
        }
        return null;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public boolean addCategory(Category category) {
        for (Category c : categories) {
            if (c.getId().equals(category.getId())) {
                return false;
            }
        }
        categories.add(category);
        return true;
    }

    public boolean updateCategory(String id, Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id) {
        for (Category c : categories) {
            if (c.getId().equals(id)) {
                categories.remove(c);
                return true;
            }
        }
        return false;
    }

}
