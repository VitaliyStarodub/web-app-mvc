package com.starodub.controller;

import com.starodub.dao.CategoryDao;
import com.starodub.model.Category;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

public class GetCategoryByIdController implements Controller {

    private final CategoryDao categoryDao;

    public GetCategoryByIdController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public ViewModel process(Request request) {
        Category category =(Category)categoryDao.findByIdJoinProduct(getIdFromRequest(request));
        ViewModel vm = ViewModel.of("category");
        vm.addAttribute("category", category);

        return vm;
    }

    private Long getIdFromRequest(Request request) {
        String object = request.getParamsByName("c_id");
        return Long.valueOf(object);
    }
}
