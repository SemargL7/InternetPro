package com.finalproject.internetpro.services.impl;

import com.finalproject.internetpro.dao.DAOrealisation.DAOTariff;
import com.finalproject.internetpro.model.Tariff;
import com.finalproject.internetpro.services.ServiceTariff;

import java.util.List;
import java.util.Optional;

/**
 * Service layer pattern
 * Service for Tariff
 * @see DAOTariff
 * @see Tariff
 */
public class ServiceTariffImpl implements ServiceTariff {

    private final DAOTariff daoTariff = DAOTariff.getInstance();

    @Override
    public Optional<Tariff> get(long id) {
        return daoTariff.get(id);
    }

    @Override
    public List<Tariff> getAll() {
        return daoTariff.getAll();
    }

    @Override
    public boolean save(Tariff tariff) {
        return daoTariff.save(tariff);
    }

    @Override
    public boolean update(Tariff tariff) {
        return daoTariff.update(tariff);
    }

    @Override
    public boolean delete(int id) {
        return daoTariff.delete(id);
    }
}
