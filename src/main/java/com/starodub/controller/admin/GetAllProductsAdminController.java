package com.starodub.controller.admin;

import com.starodub.controller.Controller;
import com.starodub.dao.ProductDao;
import com.starodub.model.Product;
import com.starodub.service.ProductService;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import java.util.List;

public class GetAllProductsAdminController implements Controller {

    private final ProductService productService;

    public GetAllProductsAdminController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ViewModel process(Request request) {
        List<Product> products = productService.findAll();
        ViewModel vm = ViewModel.of("manageProducts");
        vm.addAttribute("products", products);

        return vm;
    }
}
