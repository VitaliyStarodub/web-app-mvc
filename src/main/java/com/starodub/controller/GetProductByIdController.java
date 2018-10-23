package com.starodub.controller;

import com.starodub.dao.ProductDao;
import com.starodub.model.Product;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

public class GetProductByIdController implements Controller {

    private final ProductDao productDao;

    public GetProductByIdController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public ViewModel process(Request request) {
        Product product =(Product) productDao.findById(getIdFromRequest(request));
        ViewModel vm = ViewModel.of("product");
        vm.addAttribute("product", product);

        return vm;
    }

    private Long getIdFromRequest(Request request) {
        String object = request.getParamsByName("p_id");

        return Long.valueOf(object);
    }
}
