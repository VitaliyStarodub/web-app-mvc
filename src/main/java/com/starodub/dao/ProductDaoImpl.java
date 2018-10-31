package com.starodub.dao;

import com.starodub.model.Category;
import com.starodub.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends AbstractDao<Product, Long> implements ProductDao<Product, Long> {

    public ProductDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Product findById(Long aLong) {
        String query = "SELECT ID, NAME, PRICE, DESCRIPTION FROM PRODUCTS WHERE ID = ?;";
        PreparedStatement statement;
        ResultSet resultSet;
        Product product = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, aLong);
            resultSet = statement.executeQuery();
            product = resultSet.next() ? getProductFromResultSet(resultSet) : null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> findAll() {
        String query = "SELECT P.ID, P.NAME, P.PRICE, P.DESCRIPTION, C.CATEGORY_NAME FROM PRODUCTS P" +
                " JOIN CATEGORIES C ON P.FK_CATEGORIES = C.ID";
        PreparedStatement statement;
        ResultSet resultSet;
        List<Product> products = new ArrayList<>();

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                products.add(getProductWithCategoryFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product findByName(String name) {
        String query = "SELECT ID, NAME, PRICE, DESCRIPTION FROM PRODUCTS WHERE NAME = ?";
        PreparedStatement statement;
        ResultSet resultSet;
        Product product = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            product = resultSet.next() ? getProductFromResultSet(resultSet) : null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public void deleteByID(Long aLong) {
        String query = "DELETE FROM PRODUCTS WHERE ID = ?;";
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, aLong);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Product product) {
        String query = "INSERT INTO PRODUCTS (NAME, PRICE, DESCRIPTION, FK_CATEGORIES) VALUES (?, ?, ?, ?);";
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setLong(4, product.getCategory().getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editObject(Long id, Product product) {
        String query = "UPDATE PRODUCTS SET NAME = ?, PRICE = ?, DESCRIPTION = ?, FK_CATEGORIES = ?" +
                " WHERE ID = ?;";
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setLong(4, product.getCategory().getId());
            statement.setLong(5, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new Product(resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getDouble(3),
                            resultSet.getString(4)
                );
    }

    private Product getProductWithCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getDouble(3),
                resultSet.getString(4)
        );
        Category category = new Category();
        category.setName(resultSet.getString(5));

        product.setCategory(category);

        return product;
    }

}
