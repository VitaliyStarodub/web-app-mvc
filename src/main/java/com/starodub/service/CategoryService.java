package com.starodub.service;

import com.starodub.model.Category;
import com.starodub.model.Product;

import java.util.List;

public interface CategoryService {

    Category findByIdJoinProduct(Long id);

    Category findById(Long id);

    void save (Category category);

    void deleteByID(Long id);

    void editCategory(Long id, Category category);

    //Category findByName(String name);

    List<Category> findAll();
}
