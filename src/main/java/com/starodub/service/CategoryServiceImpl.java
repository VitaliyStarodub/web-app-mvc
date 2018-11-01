package com.starodub.service;

import com.starodub.dao.CategoryDao;
import com.starodub.dao.ProductDao;
import com.starodub.model.Category;
import com.starodub.model.Product;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;
    private final ProductDao productDao;

    public CategoryServiceImpl(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @Override
    public Category findByIdJoinProduct(Long id) {
        return (Category) categoryDao.findByIdJoinProduct(id);
    }

    @Override
    public Category findById(Long id) {
        return (Category) categoryDao.findById(id);
    }

    @Override
    public void save(Category category) {
        categoryDao.save(category);
    }

    @Override
    public void deleteByID(Long id) {
        /*List<Product> products = findByIdJoinProduct(id).getProducts();

        if(products != null) {
            for(Product product: products) {
                productDao.deleteByID(product.getId());
            }
            categoryDao.deleteByID(id);
        } else {
            categoryDao.deleteByID(id);
        }*/
        categoryDao.deleteByID(id);

    }

    @Override
    public void editCategory(Long id, Category category) {
        categoryDao.editObject(id, category);
    }

   /* @Override
    public Category findByName(String name) {
        return null;
    }*/

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
