package com.starodub.controller.admin;

import com.starodub.controller.Controller;
import com.starodub.model.Category;
import com.starodub.service.CategoryService;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import java.util.List;

public class EditCategoryController implements Controller {

    private final CategoryService categoryService;

    public EditCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ViewModel process(Request request) {
        String categoryName = request.getParamsByName("categoryName");

        Category category = new Category(categoryName);

        categoryService.editCategory(getIdFromRequest(request), category);

        List<Category> categories = categoryService.findAll();

        ViewModel vm = ViewModel.of("manageCategories");
        vm.addAttribute("categories", categories);

        return vm;
    }

    private Long getIdFromRequest(Request request) {
        String object = request.getParamsByName("c_id");

        return Long.valueOf(object);
    }
}
