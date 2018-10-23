package com.starodub;

import com.starodub.controller.*;
import com.starodub.controller.admin.AddProductController;
import com.starodub.controller.admin.GetAllCategoriesAdminController;
import com.starodub.controller.admin.GetAllProductsAdminController;
import com.starodub.dao.*;
import com.starodub.service.ProductService;
import com.starodub.service.ProductServiceImpl;
import com.starodub.service.UserService;
import com.starodub.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Factory {

    private static Connection connection;

    static {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:h2:tcp://localhost/~/java-aug-18", "sa", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static GetAllCategoriesController getAllCategoriesController() {
        return new GetAllCategoriesController(getCategoryDao(getConnection()));
    }

    public static GetCategoryByIdController getCategoryByIdController() {
        return new GetCategoryByIdController(getCategoryDao(getConnection()));
    }

    private static CategoryDao getCategoryDao(Connection connection) {
        return new CategoryDaoImpl(connection);
    }

    public static GetProductByIdController getProductByIdController() {
        return new GetProductByIdController(getProductDao(getConnection()));
    }

    private static ProductDao getProductDao(Connection connection) {
        return new ProductDaoImpl(connection);
    }

    public static LogInController getLoginController() {
        return new LogInController(getUserService());
    }

    public static RegisterController getRegisterController() {
        return new RegisterController(getUserService());
    }

    private static UserService getUserService() {
        return new UserServiceImpl(getUserDao());
    }

    public static UserDao getUserDao() {
        return new UserDaoImpl(getConnection());
    }


    public static LogOutController getLogOutController() {
        return new LogOutController();
    }

    public static GetAllCategoriesAdminController getAllCategoriesAdminController(String viewName) {
        return new GetAllCategoriesAdminController(getCategoryDao(getConnection()), viewName);
    }

    public static GetAllProductsAdminController getAllProductsAdminController() {
        return new GetAllProductsAdminController(getProductService(getConnection()));
    }

    public static AddProductController getAddProductController() {
        return new AddProductController(getProductService(getConnection()));
    }

    private static ProductService getProductService(Connection connection) {
        return new ProductServiceImpl(getProductDao(connection));
    }


}
