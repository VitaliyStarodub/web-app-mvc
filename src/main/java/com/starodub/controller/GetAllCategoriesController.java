package com.starodub.controller;

import com.starodub.dao.CategoryDao;
import com.starodub.model.Category;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import java.util.List;

public class GetAllCategoriesController implements Controller{

    private final CategoryDao categoryDao;

    public GetAllCategoriesController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public ViewModel process(Request request) {
        List<Category> categories = categoryDao.findAll();
        ViewModel vm = ViewModel.of("categories");
        vm.addAttribute("categories", categories);

        return vm;
    }
}
