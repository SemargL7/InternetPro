package com.finalproject.internetpro.services.impl;

import com.finalproject.internetpro.database.dao.DAOrealisation.DAOService;
import com.finalproject.internetpro.entity.Service;
import com.finalproject.internetpro.services.ServiceService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;
/**
 * Service layer pattern
 * Service for Service
 * @see DAOService
 * @see Service
 */
public class ServiceServiceImpl implements ServiceService {

    private static final Logger logger = Logger.getLogger(ServiceServiceImpl.class);

    private final DAOService daoService = DAOService.getInstance();

    private static ServiceServiceImpl instance;

    public static ServiceServiceImpl getInstance(){
        if(instance == null)
            instance = new ServiceServiceImpl();
        return instance;
    }

    private ServiceServiceImpl() {
    }

    @Override
    public Optional<Service> get(long id) {
        logger.info("ServiceServiceImpl | get" + id);
        return daoService.get(id);
    }

    @Override
    public List<Service> getAll() {
        logger.info("ServiceServiceImpl | getALL");
        return daoService.getAll();
    }

    @Override
    public boolean save(Service service) {
        logger.info("ServiceServiceImpl | save" + service);
        return daoService.save(service);
    }

    @Override
    public boolean update(Service service) {
        logger.info("ServiceServiceImpl | update " + service);
        return daoService.update(service);
    }

    @Override
    public boolean delete(int id) {
        logger.info("ServiceServiceImpl | delete " + id);
        return daoService.delete(id);
    }
}
