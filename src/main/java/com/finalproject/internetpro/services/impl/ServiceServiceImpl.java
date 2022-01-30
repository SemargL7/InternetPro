package com.finalproject.internetpro.services.impl;

import com.finalproject.internetpro.dao.DAOrealisation.DAOService;
import com.finalproject.internetpro.model.Service;
import com.finalproject.internetpro.services.ServiceService;

import java.util.List;
import java.util.Optional;
/**
 * Service layer pattern
 * Service for Service
 * @see DAOService
 * @see Service
 */
public class ServiceServiceImpl implements ServiceService {

    private final DAOService daoService = DAOService.getInstance();

    @Override
    public Optional<Service> get(long id) {
        return daoService.get(id);
    }

    @Override
    public List<Service> getAll() {
        return daoService.getAll();
    }

    @Override
    public boolean save(Service service) {
        return daoService.save(service);
    }

    @Override
    public boolean update(Service service) {
        return daoService.update(service);
    }

    @Override
    public boolean delete(int id) {
        return daoService.delete(id);
    }
}
