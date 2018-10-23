package com.starodub.controller.admin;

import com.starodub.controller.Controller;
import com.starodub.dao.CategoryDao;
import com.starodub.model.Category;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class GetAllCategoriesAdminController implements Controller {

    private final CategoryDao categoryDao;
    private final String VIEW_NAME;

    public GetAllCategoriesAdminController(CategoryDao categoryDao, String viewName) {
        this.categoryDao = categoryDao;
        this.VIEW_NAME = viewName;
    }

    @Override
    public ViewModel process(Request request) {
        List<Category> categories = categoryDao.findAll();
        ViewModel vm = ViewModel.of(VIEW_NAME);
        vm.addAttribute("categories", categories);

        return vm;
    }
}
