package com.finalproject.internetpro.database.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(long id) throws Exception;

    List<T> getAll() throws Exception;

    boolean save(T t) throws Exception;

    boolean update(T t) throws Exception;

    boolean delete(int id) throws Exception;
}
