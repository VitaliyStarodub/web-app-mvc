package com.starodub.dao;

import java.sql.Connection;
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
    public T findById(ID id) {
        return null;
    }

    @Override
    public void updateObject(T id) {

    }

    @Override
    public void deleteByID(ID id) {

    }

    @Override
    public List<T> findAll() {
        return null;
    }
}
