package com.frobom.sw.repository;

import com.frobom.sw.entity.User;

public interface UserRepository {

    void add(User user);

    User findByEmail(String email);
}
