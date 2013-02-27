package bikestore.db;

import javax.persistence.*;
import org.junit.*;
import static org.junit.Assert.*;

public class UserTest {
    
    private EntityManager em;
    
    private User user1,user2,user3,user4,user5,user6,user7,user8,user9,user10;
    
    @Before
    public void beforeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bikestorePU");
        em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        emptyTable();
        et.commit();
        defineUsers();
        
    }
    private void emptyTable() {
        String sql = "TRUNCATE TABLE users";
        Query q = em.createNativeQuery(sql);
        q.executeUpdate();

    }
    
    private void defineUsers() {
        user1 = new User("fjmv28"
                , "subeguara2009"
                , "Francisco Javier Martin Villuendas"
                , "fjmv28@hotmail.com"
                , "Avenida de Madrid 174"
                , "Zaragoza"
                , "España"
                , 50017);
        user2 = new User("Javier"
                , "zzzzzzzz"
                , "Francisco Javier Martin Villuendas 2"
                , "Javier@hotmail.com"
                , "Avenida de Madrid 174"
                , "Zaragoza"
                , "España"
                , 50017);
        user3 = new User("login3"
                , "passwd3"
                , "Nombre 3"
                , "email3@hotmail.com"
                , "Direccion 3"
                , "Ciudad 3"
                , "Pais 3"
                , 50003);
        user4 = new User("login4"
                , "passwd4"
                , "Nombre 4"
                , "email4@hotmail.com"
                , "Direccion 4"
                , "Ciudad 4"
                , "Pais 4"
                , 50004);
        user5 = new User("login5"
                , "passwd5"
                , "Nombre 5"
                , "email5@hotmail.com"
                , "Direccion 5"
                , "Ciudad 5"
                , "Pais 5"
                , 50005);
        user6 = new User("login6"
                , "passwd6"
                , "Nombre 6"
                , "email6@hotmail.com"
                , "Direccion 6"
                , "Ciudad 6"
                , "Pais 6"
                , 50003);
        user7 = new User("login7"
                , "passwd7"
                , "Nombre 7"
                , "email7@hotmail.com"
                , "Direccion 7"
                , "Ciudad 7"
                , "Pais 7"
                , 50003);
        user8 = new User("login8"
                , "passwd8"
                , "Nombre 8"
                , "email8@hotmail.com"
                , "Direccion 8"
                , "Ciudad 8"
                , "Pais 8"
                , 50008);
        user9 = new User("login9"
                , "passwd9"
                , "Nombre 9"
                , "email9@hotmail.com"
                , "Direccion 9"
                , "Ciudad 9"
                , "Pais 9"
                , 50009);
        user10 = new User("login10"
                , "passwd10"
                , "Nombre 10"
                , "email10@hotmail.com"
                , "Direccion 10"
                , "Ciudad 10"
                , "Pais 10"
                , 50010);
        
    }
    
    @Test
    public void createUser_test() {
        boolean result = user1.create(em);
        result = result && user2.create(em);
        assertTrue(result);
        
        assertEquals(user1,User.findById(em, user1.getId()));
        assertEquals(user2,User.findById(em, user2.getId()));
        
        assertEquals(User.count(em),2l);
        
    }
}