package com.starodub.dao;

import com.starodub.Factory;
import com.starodub.model.Category;
import com.starodub.model.Product;
import com.starodub.service.ProductService;
import com.starodub.service.ProductServiceImpl;

public class Runner {

    public static void main(String[] args) {
        Category category = new Category(1L, "mobilePhones");
        Product product = new Product("XiaomiMi888", 400.0, "New Xiaomi", category);


        /*CategoryDaoImpl categoryDao = new CategoryDaoImpl(Factory.getConnection());
        System.out.println(categoryDao.findByIdJoinProduct(1L));*/

        ProductDaoImpl productDao = new ProductDaoImpl(Factory.getConnection());
        ProductService productService = new ProductServiceImpl(productDao);
        productService.editProduct(54L, product);

       /* UserDaoImpl userDao = new UserDaoImpl(Factory.getConnection());
        System.out.println(userDao.findByToken("00fc28f4-b0e4-4105-b045-e4566817bc46"));*/


    }

}
