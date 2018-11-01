package com.starodub.web;

import com.starodub.Factory;
import com.starodub.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainServlet extends HttpServlet {

    private final static Map<Request, Controller> controllerMap = new HashMap<>();

    static {
        controllerMap.put(Request.of("GET", "/servlet/categories"), Factory.getAllCategoriesController());
        controllerMap.put(Request.of("GET", "/servlet/category"), Factory.getCategoryByIdController());
        controllerMap.put(Request.of("GET", "/servlet/product"), Factory.getProductByIdController());
        controllerMap.put(Request.of("GET", "/servlet/home"), r -> ViewModel.of("home"));
        controllerMap.put(Request.of("GET", "/servlet/login"), r -> ViewModel.of("login"));
        controllerMap.put(Request.of("GET", "/servlet/logout"), Factory.getLogOutController());
        controllerMap.put(Request.of("GET", "/servlet/register"), r -> ViewModel.of("register"));
        controllerMap.put(Request.of("GET", "/servlet/admin"), r -> ViewModel.of("adminPage"));
        controllerMap.put(Request.of("GET", "/servlet/admin/content"), r -> ViewModel.of("content"));
        controllerMap.put(Request.of("GET", "/servlet/admin/categories"), Factory.getAllCategoriesAdminController("manageCategories"));
        controllerMap.put(Request.of("GET", "/servlet/admin/products"), Factory.getAllProductsAdminController());
        controllerMap.put(Request.of("GET", "/servlet/admin/add-product"), Factory.getAllCategoriesAdminController("addProduct"));
        controllerMap.put(Request.of("GET", "/servlet/admin/add-category"), Factory.getAllCategoriesAdminController("addCategory"));
        controllerMap.put(Request.of("GET", "/servlet/admin/edit-product"), Factory.getEditProductByIdController());
        controllerMap.put(Request.of("GET", "/servlet/admin/edit-category"), Factory.getEditCategoryByIdController());
        controllerMap.put(Request.of("GET", "/servlet/admin/delete-product"), Factory.getDeleteProductController());
        controllerMap.put(Request.of("GET", "/servlet/admin/delete-category"), Factory.getDeleteCategoryController());
        controllerMap.put(Request.of("POST", "/servlet/login"), Factory.getLoginController());
        controllerMap.put(Request.of("POST", "/servlet/register"), Factory.getRegisterController());
        controllerMap.put(Request.of("POST", "/servlet/admin/add-product"), Factory.getAddProductController());
        controllerMap.put(Request.of("POST", "/servlet/admin/add-category"), Factory.getAddCategoryController());
        controllerMap.put(Request.of("POST", "/servlet/admin/edit-product"), Factory.getEditProductController());
        controllerMap.put(Request.of("POST", "/servlet/admin/edit-category"), Factory.getEditCategoryController());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Request request = Request.of(req.getMethod(), req.getRequestURI(), req.getParameterMap(), req.getCookies());

        Controller controller = controllerMap.getOrDefault(request, r -> ViewModel.of("404"));

        ViewModel vm = controller.process(request);

        sendResponse(vm, req, resp);
    }

    private void sendResponse(ViewModel vm, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirectUrl = "/WEB-INF/views/%s.jsp";
        vm.getModel().forEach(req::setAttribute);
        addCookie(vm, req, resp);
        req.getRequestDispatcher(String.format(redirectUrl, vm.getView())).forward(req, resp);
    }

    private void addCookie(ViewModel vm, HttpServletRequest req, HttpServletResponse resp) {
        if (vm.getCookies() != null) {
            for (Cookie cookie: vm.getCookies()) {
                if (cookie == null) {
                    continue;
                }
                resp.addCookie(cookie);
            }
        }
    }
}
