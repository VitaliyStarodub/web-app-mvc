package com.starodub.controller.admin;

import com.starodub.controller.Controller;
import com.starodub.model.Category;
import com.starodub.model.Product;
import com.starodub.service.ProductService;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditProductController implements Controller {

    private final ProductService productService;

    public EditProductController(ProductService productService) {
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

        productService.editProduct(getIdFromRequest(request), product);

        List<Product> products = productService.findAll();

        ViewModel vm = ViewModel.of("manageProducts");
        vm.addAttribute("products", products);


        return vm;
    }

    private Long getIdFromRequest(Request request) {
        String object = request.getParamsByName("p_id");

        return Long.valueOf(object);
    }
   /* private Long getIdFromRequest(Request request) {
        String uri = request.getUri();
        String number = uri.substring(0, uri.length() - 2);

        return Long.valueOf(number);
    }*/
}
