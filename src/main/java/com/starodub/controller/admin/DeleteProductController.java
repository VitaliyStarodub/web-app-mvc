package com.starodub.controller.admin;

import com.starodub.controller.Controller;
import com.starodub.model.Product;
import com.starodub.service.ProductService;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import java.util.List;

public class DeleteProductController implements Controller {

    private final ProductService productService;

    public DeleteProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ViewModel process(Request request) {
        productService.deleteByID(getIdFromRequest(request));

        ViewModel vm = ViewModel.of("manageProducts");
        vm.addAttribute("msg_del", true);

        List<Product> products = productService.findAll();
        vm.addAttribute("products", products);

        return vm;
    }

    private Long getIdFromRequest(Request request) {
        String object = request.getParamsByName("p_id");

        return Long.valueOf(object);
    }
}
