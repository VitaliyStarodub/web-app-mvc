package com.starodub.controller.admin;

import com.starodub.controller.Controller;
import com.starodub.dao.CategoryDao;
import com.starodub.dao.ProductDao;
import com.starodub.model.Category;
import com.starodub.model.Product;
import com.starodub.service.ProductService;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import java.util.List;

public class EditProductByIdController implements Controller {

    private final CategoryDao categoryDao;
    private final ProductDao productDao;

    public EditProductByIdController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @Override
    public ViewModel process(Request request) {
        List<Category> categories = categoryDao.findAll();
        Product product = (Product) productDao.findById(getIdFromRequest(request));
        ViewModel vm = ViewModel.of("editProduct");
        vm.addAttribute("categories", categories);
        vm.addAttribute("product", product);

        return vm;
    }

    private Long getIdFromRequest(Request request) {
        String object = request.getParamsByName("p_id");

        return Long.valueOf(object);
    }
}
