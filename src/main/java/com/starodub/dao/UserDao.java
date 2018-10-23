package com.starodub.dao;

import com.starodub.model.User;

public interface UserDao<T, ID> extends GenericDao<T, ID> {

    User addUser(User user);

    User findByEmail(String email);

    User findByToken(String token);

}
