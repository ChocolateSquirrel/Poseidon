package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);
}
