package com.starodub.controller.admin;

import com.starodub.controller.Controller;
import com.starodub.model.Category;
import com.starodub.service.CategoryService;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import java.util.List;

public class DeleteCategoryController implements Controller {

    private final CategoryService categoryService;

    public DeleteCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ViewModel process(Request request) {
        categoryService.deleteByID(getIdFromRequest(request));

        ViewModel vm = ViewModel.of("manageCategories");
        vm.addAttribute("msg_del", true);

        List<Category> categories = categoryService.findAll();
        vm.addAttribute("categories", categories);

        return vm;
    }

    private Long getIdFromRequest(Request request) {
        String object = request.getParamsByName("c_id");

        return Long.valueOf(object);
    }
}
