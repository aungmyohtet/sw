package com.frobom.sw.service;

import java.util.List;

import com.frobom.sw.entity.User;

public interface UserService {

    void add(User user);

    User findById(long id);

    List<User> findAll();

    User findByEmail(String email);

}
