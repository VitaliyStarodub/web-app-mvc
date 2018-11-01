package com.starodub.dao;

import com.starodub.model.Category;

public interface CategoryDao<T, ID> extends GenericDao<T, ID> {
    T findByIdJoinProduct(ID id);
}
