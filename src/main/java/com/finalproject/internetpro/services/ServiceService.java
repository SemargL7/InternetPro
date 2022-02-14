package com.finalproject.internetpro.services;

import com.finalproject.internetpro.entity.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceService {
    Optional<Service> get(long id);

    List<Service> getAll();

    boolean save(Service service);

    boolean update(Service service);

    boolean delete(int id);
}
