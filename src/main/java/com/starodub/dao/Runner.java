package com.starodub.dao;

import com.starodub.Factory;
import com.starodub.model.Category;
import com.starodub.model.Product;
import com.starodub.service.CategoryService;
import com.starodub.service.CategoryServiceImpl;
import com.starodub.service.ProductService;
import com.starodub.service.ProductServiceImpl;

import java.util.List;

public class Runner {

    public static void main(String[] args) {
        /*Category category = new Category("Notes");
        Product product = new Product("XiaomiMi888", 400.0, "New Xiaomi", category);*/


        /*CategoryDaoImpl categoryDao = new CategoryDaoImpl(Factory.getConnection());
        System.out.println(categoryDao.findByIdJoinProduct(1L));*/

        ProductDaoImpl productDao = new ProductDaoImpl(Factory.getConnection());
        CategoryDaoImpl categoryDao = new CategoryDaoImpl(Factory.getConnection());
        //ProductService productService = new ProductServiceImpl(productDao);
        CategoryService categoryService = new CategoryServiceImpl(categoryDao, productDao);

        //List<Product> products = categoryService.findByIdJoinProduct(3L).getProducts();

        /*Category category = categoryService.findById(102L);
        List<Product> products;

        if(!category.isProductsPresent()) {
            System.out.println("no products");
        } else {
            products = categoryService.findByIdJoinProduct(3L).getProducts();
            for(Product product: products) {
                System.out.println(product);
            }
        }*/

        System.out.println(categoryService.findByIdJoinProduct(3L));



        //System.out.println(categoryDao.findByIdJoinProduct(1L));
       /* UserDaoImpl userDao = new UserDaoImpl(Factory.getConnection());
        System.out.println(userDao.findByToken("00fc28f4-b0e4-4105-b045-e4566817bc46"));*/


    }

}
