package com.starodub.controller.admin;

import com.starodub.controller.Controller;
import com.starodub.model.Category;
import com.starodub.model.Product;
import com.starodub.service.ProductService;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import java.util.List;

public class AddProductController implements Controller {

    private final ProductService productService;

    public AddProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ViewModel process(Request request) {
        String productName = request.getParamsByName("productName");
        Double productPrice = Double.valueOf(request.getParamsByName("price"));
        String productDescription = request.getParamsByName("description");
        Long categoryId = Long.valueOf(request.getParamsByName("categoryId"));

        Product product = new Product(productName, productPrice, productDescription);
        Category category = new Category();
        category.setId(categoryId);
        product.setCategory(category);

        productService.save(product);

        List<Product> products = productService.findAll();

        ViewModel vm = ViewModel.of("manageProducts");
        vm.addAttribute("products", products);

        return vm;
    }
}
