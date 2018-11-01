package com.starodub.controller.admin;

import com.starodub.controller.Controller;
import com.starodub.model.Category;
import com.starodub.service.CategoryService;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

public class EditCategoryByIdController implements Controller {

    private final CategoryService categoryService;

    public EditCategoryByIdController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ViewModel process(Request request) {
        Category category = categoryService.findById(getIdFromRequest(request));
        ViewModel vm = ViewModel.of("editCategory");
        vm.addAttribute("category", category);

        return vm;
    }

    private Long getIdFromRequest(Request request) {
        String object = request.getParamsByName("c_id");

        return Long.valueOf(object);
    }
}
