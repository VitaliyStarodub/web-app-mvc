package com.starodub.controller.admin;

import com.starodub.controller.Controller;
import com.starodub.model.Category;
import com.starodub.service.CategoryService;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryController implements Controller {

    private final CategoryService categoryService;

    public AddCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ViewModel process(Request request) {
        String categoryName = request.getParamsByName("categoryName");

        Category category = new Category(categoryName);

        categoryService.save(category);

        List<Category> categories = categoryService.findAll();

        ViewModel vm = ViewModel.of("manageCategories");
        vm.addAttribute("categories", categories);

        return vm;
    }
}
