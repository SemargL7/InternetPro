package com.finalproject.internetpro.services;

import com.finalproject.internetpro.model.Tariff;

import java.util.List;
import java.util.Optional;

public interface ServiceTariff {
    Optional<Tariff> get(long id);

    List<Tariff> getAll();

    boolean save(Tariff tariff);

    boolean update(Tariff tariff);

    boolean delete(int id);
}
