package com.starodub.dao;

import com.starodub.model.Product;

public interface ProductDao<T, ID> extends GenericDao<T, ID> {

    Product findByName(String name);

}
