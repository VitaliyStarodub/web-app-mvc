package com.starodub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AbstractDao<T, ID> implements GenericDao<T, ID> {

    final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(T type) {

    }

    @Override
    public T findById(ID id) { // remove nulls
        T object = null;
        String query = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            statement = connection.prepareStatement(query);
            statement.setObject(1, id);
            resultSet = statement.executeQuery();
            object = resultSet.next() ? null : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public void editObject(ID id, T object) {

    }

    @Override
    public void deleteByID(ID id) {

    }

    @Override
    public List<T> findAll() {
        return null;
    }
}
