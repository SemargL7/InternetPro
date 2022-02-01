import com.finalproject.internetpro.database.Database;
import com.finalproject.internetpro.model.User;
import com.finalproject.internetpro.model.UserAccess;
import com.finalproject.internetpro.services.impl.ServiceUserImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;

public class TestServices {
    @Test
    public void testUserService(){
        ServiceUserImpl serviceUser = new ServiceUserImpl();
        Assertions.assertFalse(serviceUser.register(new User()));
        Assertions.assertFalse(serviceUser.update(new User()));

        User testUser = new User();
        testUser.setId(999);
        testUser.setName("test");
        testUser.setSurname("test");
        testUser.setEmail("test@gmail.com");
        testUser.setPassword("test");
        testUser.setBalance(100);
        testUser.setDateOfBirth(Date.valueOf("2001-01-01"));
        testUser.setBlocked(true);
        testUser.setUserAccess(UserAccess.USER);
        System.out.println(testUser);

        Assertions.assertTrue(serviceUser.register(testUser));

        Assertions.assertTrue(serviceUser.get(999).isPresent());

        Assertions.assertTrue(serviceUser.get(999).get().isBlocked());

        Assertions.assertTrue(serviceUser.blockStatusUser(999,false));

        Assertions.assertFalse(serviceUser.get(999).get().isBlocked());

        Assertions.assertNotNull(serviceUser.loggingUser(testUser.getEmail(), testUser.getPassword()));

        Assertions.assertNotEquals(testUser, serviceUser.get(999).get());

        testUser.setBlocked(false);
        Assertions.assertEquals(testUser, serviceUser.get(999).get());

        Assertions.assertTrue(serviceUser.delete(999));
        clear("internetprovider.Users");
    }

    private void clear(String table)
    {
        try {
            String sql = "ALTER TABLE "+table+" AUTO_INCREMENT =1;";
            Connection con = Database.getConnection();
            con.prepareStatement(sql).executeUpdate();
            System.out.println("clear");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
    }
}
