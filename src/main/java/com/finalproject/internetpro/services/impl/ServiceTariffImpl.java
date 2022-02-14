package com.finalproject.internetpro.services.impl;

import com.finalproject.internetpro.database.dao.DAOrealisation.DAOTariff;
import com.finalproject.internetpro.entity.Tariff;
import com.finalproject.internetpro.services.ServiceTariff;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Service layer pattern
 * Service for Tariff
 * @see DAOTariff
 * @see Tariff
 */
public class ServiceTariffImpl implements ServiceTariff {

    private static final Logger logger = Logger.getLogger(ServiceTariffImpl.class);

    private final DAOTariff daoTariff = DAOTariff.getInstance();

    private static ServiceTariffImpl instance;

    public static ServiceTariffImpl getInstance(){
        if(instance == null)
            instance = new ServiceTariffImpl();
        return instance;
    }

    private ServiceTariffImpl() {
    }

    @Override
    public Optional<Tariff> get(long id) {
        logger.info("ServiceTariffImpl | get" + id);
        return daoTariff.get(id);
    }

    @Override
    public List<Tariff> getAll() {
        logger.info("ServiceTariffImpl | getALL");
        return daoTariff.getAll();
    }

    @Override
    public boolean save(Tariff tariff) {
        logger.info("ServiceTariffImpl | save" + tariff);
        return daoTariff.save(tariff);
    }

    @Override
    public boolean update(Tariff tariff) {
        logger.info("ServiceTariffImpl | update " + tariff);
        return daoTariff.update(tariff);
    }

    @Override
    public boolean delete(int id) {
        logger.info("ServiceTariffImpl | delete " + id);
        return daoTariff.delete(id);
    }
}
