package com.finalproject.internetpro.services.impl;

import com.finalproject.internetpro.dao.DAOrealisation.DAOUser;
import com.finalproject.internetpro.model.User;
import com.finalproject.internetpro.services.ServiceUser;

import java.util.List;
import java.util.Optional;

public class ServiceUserImpl implements ServiceUser {

    private final DAOUser daoUser = DAOUser.getInstance();

    @Override
    public Optional<User> get(int id) {
        return daoUser.get(id);
    }

    @Override
    public List<User> getAll() {
        return daoUser.getAll();
    }

    @Override
    public Optional<User> loggingUser(String email, String password){
        Integer id = daoUser.loggingUser(email,password);
        if(id != null){
            return daoUser.get(id);
        }
        else return Optional.empty();
    }

    @Override
    public boolean register(User user) {
        if(daoUser.mailFree(user.getEmail())){
            daoUser.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(User user){
        Optional<User> oldUser = daoUser.get(user.getId());
        if(oldUser.isPresent()) {
            user.getTariffs().stream()
                    .filter(x -> !oldUser.get().getTariffs().contains(x))
                    .forEach(x -> daoUser.connectTariffConnection(user.getId(), x.getId()));
            oldUser.get().getTariffs().stream()
                    .filter(x -> !user.getTariffs().contains(x))
                    .forEach(x -> daoUser.deleteTariffConnection(user.getId(), x.getId()));
            return true;
        }
        return false;
    }
    @Override
    public boolean blockStatusUser(int id, boolean block){
        return daoUser.blockStatusUser(id,block);
    }

    @Override
    public boolean delete(int id) {
        return daoUser.delete(id);
    }

    @Override
    public boolean updateAllUsersBalances() {
        return daoUser.updateAllUsersBalances();
    }
}
