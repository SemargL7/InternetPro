package com.finalproject.internetpro.services;

import com.finalproject.internetpro.model.User;

import java.util.List;
import java.util.Optional;

public interface ServiceUser {
    Optional<User> get(int id);

    List<User> getAll();

    Optional<User> loggingUser(String email, String password);

    boolean register(User user);

    boolean update(User user);

    boolean blockStatusUser(int id, boolean block);

    boolean delete(int id);

    boolean updateStatus(int id);
}
