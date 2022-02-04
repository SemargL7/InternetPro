package com.finalproject.internetpro.services.impl;

import com.finalproject.internetpro.dao.DAOrealisation.DAOUser;
import com.finalproject.internetpro.model.User;
import com.finalproject.internetpro.model.UserAccess;
import com.finalproject.internetpro.services.ServiceUser;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Service layer pattern
 * Service for User
 * @see DAOUser
 * @see User
 */
public class ServiceUserImpl implements ServiceUser {

    private static final Logger logger = Logger.getLogger(ServiceUserImpl.class);

    private final DAOUser daoUser = DAOUser.getInstance();

    /**
     * Function is getting User from Database
     * @param id User`s id
     * @return return the User with a specific id OR empty Optional
     */
    @Override
    public Optional<User> get(int id) {
        logger.info("ServiceUserImpl | get " + id);
        return daoUser.get(id);
    }

    /**
     * Function is getting list with all users
     * @return List of Users
     */
    @Override
    public List<User> getAll() {
        logger.info("ServiceUserImpl | getAll");
        return daoUser.getAll();
    }

    /**
     * Function is getting User which contain inputted email and password
     * @param email User`s email
     * @param password User`s password
     * @return Optional User or empty
     */
    @Override
    public Optional<User> loggingUser(String email, String password){
        logger.info("ServiceUserImpl | loggingUser " + email + " " + password);
        Integer id = daoUser.loggingUser(email,password);
        if(id != null){
            return daoUser.get(id);
        }
        else return Optional.empty();
    }

    /**
     * Function is inserting new User by checking email(does is free), and saving in database
     * @param user User which probably will be inserted
     * @return boolean, if user have saved - true, else - false
     */
    @Override
    public boolean register(User user) {
        logger.info("ServiceUserImpl | register " + user);
        if(daoUser.mailFree(user.getEmail())){
            return daoUser.save(user);
        }
        return false;
    }

    /**
     * Function is updating User and user`s tariff list
     * @param user user
     * @return boolean, if user have saved - true, else - false
     */
    @Override
    public boolean update(User user){
        logger.info("ServiceUserImpl | update " + user);
        Optional<User> oldUser = daoUser.get(user.getId());
        if(oldUser.isPresent()) {
            user.getTariffs().stream()
                    .filter(x -> !oldUser.get().getTariffs().contains(x))
                    .forEach(x ->{
                        if(user.setBalance(user.getBalance() - x.getCost())){
                            daoUser.connectTariffConnection(user.getId(), x.getId());
                            user.setBlocked(false);
                        }
                    });
            oldUser.get().getTariffs().stream()
                    .filter(x -> !user.getTariffs().contains(x))
                    .forEach(x -> daoUser.deleteTariffConnection(user.getId(), x.getId()));

            daoUser.update(user);
            return true;
        }
        return false;
    }

    /**
     * Function is changing block status of User
     * @param id User`s id
     * @param block block status(true - block, false - unblock)
     * @return boolean, if user have changed block status - true, else - false
     */
    @Override
    public boolean blockStatusUser(int id, boolean block){
        logger.info("ServiceUserImpl | blockStatusUser " + id + " " + block);
        Optional<User> user = daoUser.get(id);
        if(user.isPresent() && user.get().getUserAccess() == UserAccess.USER ){
            return daoUser.blockStatusUser(id,block);
        }
        return false;
    }

    /**
     * Function is deleting User
     * @param id User`s id
     * @return boolean, if user have deleted - true, else - false
     */
    @Override
    public boolean delete(int id) {
        logger.info("ServiceUserImpl | delete " + id);
        return daoUser.delete(id);
    }

    /**
     * Function is updating all user`s block statuses and balances
     * @return boolean, if all users have updated - true, else - false
     */
    @Override
    public boolean updateAllUsersBalances() {
        logger.info("ServiceUserImpl | updateAllUsersBalances");
        return daoUser.updateAllUsersBalances();
    }
}
