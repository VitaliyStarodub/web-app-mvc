package com.starodub.dao;

import java.util.List;

public interface GenericDao<T, ID> {

    void save(T type);

    T findById(ID id);

    void updateObject(T id);

    void deleteByID(ID id);

    List<T> findAll();

}
