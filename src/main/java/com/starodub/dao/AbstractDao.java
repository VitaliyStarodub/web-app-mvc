package com.starodub.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbstractDao<T, ID> implements GenericDao<T, ID> {

    final Connection connection;
    private String type = getParameterizedTypes(this).getTypeName();
    private Class<?> clazzT = getClass(type);
    private QueryBuilder<T> queryBuilder = new QueryBuilder<>();
    private PrepareStatementsBuilder<T, ID> prepareStatementsBuilder = new PrepareStatementsBuilder<>();

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(T type) {
        queryBuilder = new QueryBuilder<>(clazzT);
        String query = queryBuilder.insertQuery(clazzT);
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(query);
            prepareStatementsBuilder.prepareStatementForInsert(statement, type);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T findById(ID id) {
        T obj = null;
        String query = queryBuilder.findByIdQuery(clazzT);
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            resultSet = statement.executeQuery();
            obj = resultSet.next() ? prepareStatementsBuilder.getObjectFromResultSet(resultSet, clazzT) : null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }


    @Override
    public void editObject(ID id, T object) {
        queryBuilder = new QueryBuilder<>(clazzT);
        String query = queryBuilder.updateQuery(clazzT);
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(query);
            prepareStatementsBuilder.prepareStatementForUpdate(statement, object, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByID(ID id) {
        queryBuilder = new QueryBuilder<>(clazzT);
        String query = queryBuilder.deleteQuery(clazzT);
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> findAll() {
        queryBuilder = new QueryBuilder<>(clazzT);
        String query = queryBuilder.findAllQuery(clazzT);
        Statement statement;
        ResultSet resultSet;
        List<T> list = new ArrayList<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                list.add(prepareStatementsBuilder.getObjectFromResultSet(resultSet, clazzT));
            }

        } catch (SQLException e) {
            System.out.println("Can't find objects");
        }

        return list;
    }


    private Class<?> getClass(String className) {
        Class<?> someClass = null;
        try {
            someClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return someClass;
    }

    private Type getParameterizedTypes(Object object) {
        Type superclassType = object.getClass().getGenericSuperclass();
        if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
            return null;
        }
        return ((ParameterizedType) superclassType).getActualTypeArguments()[0];
    }
}
