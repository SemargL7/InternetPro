package com.finalproject.internetpro.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(long id) throws Exception;

    List<T> getAll() throws Exception;

    void save(T t) throws Exception;

    void update(T t) throws Exception;

    void delete(int id) throws Exception;
}
