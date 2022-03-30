import com.finalproject.internetpro.database.dao.DAOrealisation.DAOService;
import com.finalproject.internetpro.database.dao.DAOrealisation.DAOTariff;
import com.finalproject.internetpro.database.dao.DAOrealisation.DAOUser;
import com.finalproject.internetpro.database.DBManager;
import com.finalproject.internetpro.entity.Service;
import com.finalproject.internetpro.entity.Tariff;
import com.finalproject.internetpro.entity.User;
import com.finalproject.internetpro.entity.UserAccess;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

//public class TestDao {
//    private DAOService daoService = DAOService.getInstance();
//    private List<Service> serviceList = new ArrayList<>();
//
//    {
//        serviceList.add(new Service(990,"Music"));
//        serviceList.add(new Service(991,"Music1"));
//        serviceList.add(new Service(992,"Music2"));
//        serviceList.add(new Service(993,"Music3"));
//        serviceList.add(new Service(994,"Music4"));
//        serviceList.add(new Service(995,"Music5"));
//        serviceList.add(new Service(996,"Music6"));
//    }
//
//    private List<Service> newServiceList = new ArrayList<>();
//
//    {
//        newServiceList.add(new Service(990,"newMusic"));
//        newServiceList.add(new Service(991,"newMusic1"));
//        newServiceList.add(new Service(992,"newMusic2"));
//        newServiceList.add(new Service(993,"newMusic3"));
//        newServiceList.add(new Service(994,"newMusic4"));
//        newServiceList.add(new Service(995,"newMusic5"));
//        newServiceList.add(new Service(996,"newMusic6"));
//    }
//
//    private List<Service> nullList = new ArrayList<>();
//    {
//        nullList.add(new Service(0,null));
//        nullList.add(new Service(0,"null2"));
//        nullList.add(new Service(0,"null3"));
//        nullList.add(null);
//    }
//
//    @Test
//    public void testDAOService() throws Exception {
//        //add
//        serviceList.stream().forEach(x->{
//            daoService.save(x);
//            Assertions.assertEquals(x.getId(), daoService.get(x.getId()).get().getId());
//        });
//        //update
//        newServiceList.stream().forEach(x->{
//            Assertions.assertTrue(daoService.get(x.getId()).isPresent());
//            daoService.update(x);
//        });
//        serviceList.stream().forEach(x->{
//            Assertions.assertFalse(daoService
//                    .get(x.getId()).get()
//                    .getServiceName()
//                    .equalsIgnoreCase(x.getServiceName()));
//        });
//        newServiceList.stream().forEach(x->{
//            Assertions.assertTrue(daoService
//                    .get(x.getId()).get()
//                    .getServiceName()
//                    .equalsIgnoreCase(x.getServiceName()));
//        });
//        //delete
//        serviceList.stream().forEach(x->{
//            Assertions.assertTrue(daoService.get(x.getId()).isPresent());
//            daoService.delete(x.getId());
//            Assertions.assertFalse(daoService.get(x.getId()).isPresent());
//        });
//        clear("internetprovider.Services");
//    }
//
//    @Test
//    public void testNullDAOService() throws Exception {
//        //add
//        nullList.stream().forEach(x->{
//            daoService.save(x);
//            if(x!=null)
//            Assertions.assertTrue(daoService.getAll().stream()
//                    .anyMatch(y->y.getServiceName().equalsIgnoreCase(y.getServiceName())));
//        });
//        //delete
//        nullList.stream().filter(x->x!=null &&x.getServiceName()!=null).forEach(x->{
//            Assertions.assertTrue(daoService.getAll().stream()
//                    .anyMatch(y->x.getServiceName().equalsIgnoreCase(y.getServiceName())));
//            daoService.delete(daoService.getAll().stream()
//                    .filter(y->x.getServiceName().equalsIgnoreCase(y.getServiceName()))
//                    .findFirst().get().getId());
//            Assertions.assertFalse(daoService.getAll().stream()
//                    .anyMatch(y->x.getServiceName().equalsIgnoreCase(y.getServiceName())));
//        });
//        clear("internetprovider.Services");
//    }
//
///*
//========================================================================================================================
//========================================================================================================================
// */
//    private final DAOTariff daoTariff = DAOTariff.getInstance();
//    private final List<Tariff> tariffList = new ArrayList<>();
//
//    {
//        tariffList.add(new Tariff(990, daoService.get(1).get(), 1000.0, 10));
//        tariffList.add(new Tariff(991, daoService.get(1).get(), 1000.0, 10));
//        tariffList.add(new Tariff(992, daoService.get(1).get(), 1000.0, 10));
//        tariffList.add(new Tariff(993, daoService.get(1).get(), 1000.0, 10));
//        tariffList.add(new Tariff(994, daoService.get(1).get(), 1000.0, 10));
//    }
//
//    private final List<Tariff> newTariffList = new ArrayList<>();
//
//    {
//        newTariffList.add(new Tariff(990, daoService.get(2).get(), 5000.0, 30));
//        newTariffList.add(new Tariff(991, daoService.get(3).get(), 555.0, 21));
//        newTariffList.add(new Tariff(992, daoService.get(4).get(), 100.0, 75));
//        newTariffList.add(new Tariff(993, daoService.get(3).get(), 1100.0, 100));
//        newTariffList.add(new Tariff(994, daoService.get(1).get(), 100.0, 10));
//    }
//
//    private final List<Tariff> nullTariffList = new ArrayList<>();
//    {
//        nullTariffList.add(new Tariff(0, daoService.get(2).get(), 3000.0, 11));
//        nullTariffList.add(new Tariff(990, null, 3000.0, 11));
//        nullTariffList.add(null);
//    }
//
//    @Test
//    public void testDAOTariff() throws Exception {
//        //add
//        tariffList.forEach(x->{
//            daoTariff.save(x);
//            Assertions.assertEquals(x, daoTariff.get(x.getId()).get());
//        });
//        //update
//        newTariffList.forEach(x->{
//            Assertions.assertTrue(daoTariff.get(x.getId()).isPresent());
//            daoTariff.update(x);
//        });
//        tariffList.forEach(x->{
//            Assertions.assertNotEquals(daoTariff
//                    .get(x.getId()).get(), x);
//        });
//        newTariffList.forEach(x->{
//            System.out.println(x);
//            Assertions.assertEquals(daoTariff
//                    .get(x.getId()).get(), x);
//        });
//        //delete
//        tariffList.forEach(x->{
//            Assertions.assertTrue(daoTariff.get(x.getId()).isPresent());
//            daoTariff.delete(x.getId());
//            Assertions.assertFalse(daoTariff.get(x.getId()).isPresent());
//        });
//        clear("internetprovider.Tariff");
//    }
//
//    @Test
//    public void testNullDAOTariff() throws Exception {
//        //add
//        nullTariffList.forEach(x->{
//            daoTariff.save(x);
//        });
//
//        Assertions.assertTrue(daoTariff.getAll().stream()
//                .anyMatch(y->nullTariffList.get(0).equals(y)));
//        Assertions.assertFalse(daoTariff.getAll().stream()
//                .anyMatch(y->nullTariffList.get(1).equals(y)));
//        Assertions.assertFalse(daoTariff.getAll().stream()
//                .anyMatch(y->y.equals(nullTariffList.get(2))));
//
//        //delete
//        daoTariff.delete(daoTariff.getAll().stream().filter(y->y.equals(nullTariffList.get(0))).findFirst().get().getId());
//
//
//        Assertions.assertFalse(daoTariff.getAll().stream()
//                .anyMatch(y->nullTariffList.get(0).equals(y)));
//
//        clear("internetprovider.Tariff");
//    }
///*
//========================================================================================================================
//========================================================================================================================
// */
//    private final DAOUser daoUser = DAOUser.getInstance();
//    private final List<User> userList = new ArrayList<>();
//
//    {
//        try {
//            userList.add(new User(100, "Test1", "SurTest1", "Test1@gmail.com",
//                    "1", 3000, Date.valueOf("2001-10-10"), true, UserAccess.USER));
//            userList.add(new User(101, "Test2", "SurTest2", "Test2@gmail.com",
//                    "2", 3000, Date.valueOf("2001-10-10"), true, UserAccess.USER));
//            userList.add(new User(102, "Test3", "SurTest3", "Test3@gmail.com",
//                    "3", 3000, Date.valueOf("2001-10-10"), true, UserAccess.USER));
//            userList.add(new User(103, "Test4", "SurTest4", "Test4@gmail.com",
//                    "4", 3000, Date.valueOf("2001-10-10"), true, UserAccess.USER));
//            userList.add(new User(104, "Test5", "SurTest5", "Test5@gmail.com",
//                    "5", 3000, Date.valueOf("2001-10-10"), true, UserAccess.USER));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    private final List<User> newUserList = new ArrayList<>();
//
//    {
//        try{
//            newUserList.add(new User(100, "Test1RE", "SurTest1RE", "Test1RE@gmail.com",
//                    "1", 0, Date.valueOf("2001-10-10"), true, UserAccess.USER));
//            newUserList.add(new User(101, "Test2RE", "SurTest2RE", "Test2RE@gmail.com",
//                    "2", 3700, Date.valueOf("2001-10-10"), true, UserAccess.USER));
//            newUserList.add(new User(102, "Test3RE", "SurTest3RE", "Test3RE@gmail.com",
//                    "3", 3500, Date.valueOf("2001-10-10"), true, UserAccess.USER));
//            newUserList.add(new User(103, "Test4RE", "SurTest4RE", "Test4RE@gmail.com",
//                    "4", 4000, Date.valueOf("2001-10-10"), true, UserAccess.USER));
//            newUserList.add(new User(104, "Test5RE", "SurTest5RE", "Test5RE@gmail.com",
//                    "5", 300, Date.valueOf("2001-10-10"), true, UserAccess.USER));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    private final List<User> nullUserList = new ArrayList<>();
//    {
//        try{
//            nullUserList.add(new User(0, "Test0", "SurTest0", "Test0@gmail.com",
//                    "0", 0, Date.valueOf("2001-10-10"), true, UserAccess.USER));
//            nullUserList.add(null);
//            nullUserList.add(null);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testDAOUser() throws Exception {
//        //add
//        userList.forEach(x->{
//            daoUser.save(x);
//            Assertions.assertEquals(x, daoUser.get(x.getId()).get());
//        });
//        //update
//        newUserList.forEach(x->{
//            Assertions.assertTrue(daoUser.get(x.getId()).isPresent());
//            daoUser.update(x);
//        });
//        userList.forEach(x->{
//            Assertions.assertNotEquals(daoUser
//                    .get(x.getId()).get(), x);
//        });
//        newUserList.forEach(x->{
//            System.out.println(x);
//            Assertions.assertEquals(daoUser
//                    .get(x.getId()).get(), x);
//        });
//        //delete
//        userList.forEach(x->{
//            Assertions.assertTrue(daoUser.get(x.getId()).isPresent());
//            daoUser.delete(x.getId());
//            Assertions.assertFalse(daoUser.get(x.getId()).isPresent());
//        });
//        clear("internetprovider.Users");
//    }
//
//    @Test
//    public void testNullDAOUser() throws Exception {
//        nullUserList.forEach(x->{
//            daoUser.save(x);
//        });
//
//        Assertions.assertTrue(daoUser.getAll().stream()
//                .anyMatch(y->nullUserList.get(0).equals(y)));
//        Assertions.assertFalse(daoUser.getAll().stream()
//                .anyMatch(y->y.equals(nullUserList.get(1))));
//        Assertions.assertFalse(daoUser.getAll().stream()
//                .anyMatch(y->y.equals(nullUserList.get(2))));
//
//        //delete
//        daoUser.delete(daoUser.getAll().stream().filter(y->y.equals(nullUserList.get(0))).findFirst().get().getId());
//
//
//        Assertions.assertFalse(daoUser.getAll().stream()
//                .anyMatch(y->nullUserList.get(0).equals(y)));
//
//        clear("internetprovider.Users");
//    }
//
//    //clearing
//    private void clear(String table)
//    {
//        try {
//            String sql = "ALTER TABLE "+table+" AUTO_INCREMENT =1;";
//            Connection con = DBManager.getInstance().getConnection();
//            con.prepareStatement(sql).executeUpdate();
//            System.out.println("clear");
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println(e);
//        }
//    }
//
//}
