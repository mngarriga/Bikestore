package bikestore.db;

import java.security.*;
import javax.persistence.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.util.logging.*;

public class UserTest {
    
    private EntityManager em;
    
    private User user1,user2,user3,user4,user5,user6,user7,user8,user9,user10;
    
    @Before
    public void beforeTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bikestorePU");
        em = emf.createEntityManager();
        emptyTable();
        defineAllUsers();
        
    }
    
    private void emptyTable() {
        String sql = "TRUNCATE TABLE users";
        Query q = em.createNativeQuery(sql);
        EntityTransaction et = em.getTransaction();
        et.begin();
        q.executeUpdate();
        et.commit();
    }
    
    private void defineAllUsers() {
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
    
    private void createAllUsers() {
        user1.create(em);
        user2.create(em);
        user3.create(em);
        user4.create(em);
        user5.create(em);
        user6.create(em);
        user7.create(em);
        user8.create(em);
        user9.create(em);
        user10.create(em);
    }
    
    private static String getSha(String s) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            return new String(sha.digest(s.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
        }
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
    
    @Test
    public void countUsers_test() {
        assertTrue(User.count(em) == 0l);
        createAllUsers();
        assertTrue(User.count(em) == 10l);
    }
    
    @Test
    public void findById_test() {
        createAllUsers();
        
        assertEquals(user1,user1.findById(em, user1.getId()));
        assertEquals(user2,user2.findById(em, user2.getId()));
        assertEquals(user3,user3.findById(em, user3.getId()));
        assertEquals(user4,user4.findById(em, user4.getId()));
        assertEquals(user5,user5.findById(em, user5.getId()));
        assertEquals(user6,user6.findById(em, user6.getId()));
        assertEquals(user7,user7.findById(em, user7.getId()));
        assertEquals(user8,user8.findById(em, user8.getId()));
        assertEquals(user9,user9.findById(em, user9.getId()));
        assertEquals(user10,user10.findById(em, user10.getId()));
    }
    
    @Test
    public void findAll_test() {
        createAllUsers();
        
        List<User> expected = new ArrayList<User>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);
        expected.add(user4);
        expected.add(user5);
        expected.add(user6);
        expected.add(user7);
        expected.add(user8);
        expected.add(user9);
        expected.add(user10);
        
        assertEquals(expected,User.findAll(em));
    }
    
    @Test
    public void findByPage_test() {
        createAllUsers();
        
        List<User> expected = new ArrayList<User>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);
        expected.add(user4);
        expected.add(user5);
        assertEquals(expected,User.findByPage(em, 1, 5));
        
        expected.clear();
        expected.add(user6);
        expected.add(user7);
        expected.add(user8);
        expected.add(user9);
        expected.add(user10);
        assertEquals(expected,User.findByPage(em, 2, 5));
    }
    
    @Test
    public void exists_test() {
        createAllUsers();
        
        assertTrue(User.exists(em, "fjmv28"));
        assertTrue(User.exists(em, "Javier"));
        assertTrue(User.exists(em, "login3"));
        assertTrue(User.exists(em, "login4"));
        assertTrue(User.exists(em, "login5"));
        assertTrue(User.exists(em, "login6"));
        assertTrue(User.exists(em, "login7"));
        assertTrue(User.exists(em, "login8"));
        assertTrue(User.exists(em, "login9"));
        assertTrue(User.exists(em, "login10"));
        
        assertFalse(User.exists(em, "foo"));
        assertFalse(User.exists(em, "bar"));
    }
    
    @Test
    public void login_test() {
        createAllUsers();
        
        assertEquals(user1,User.loginUser(em, "fjmv28", "subeguara2009"));
        assertEquals(null,User.loginUser(em, "login2", "zzzzzzzz"));
        assertEquals(user2,User.loginUser(em, "Javier", "zzzzzzzz"));
        assertEquals(null,User.loginUser(em, "login10", "passwdNo"));
    }
    
    @Test
    public void update_test() {
        createAllUsers();

        User userPrevio = User.findById(em, user1.getId());
        userPrevio.setLogin("login1");
        userPrevio.setPasswd("passwd1");
        userPrevio.setFullName("Nombre 1");
        userPrevio.setAddress("Direccion 1");
        userPrevio.setCity("Ciudad 1");
        userPrevio.setCountry("Pais 1");
        userPrevio.setEmail("email1@hotmail.com");
        userPrevio.setZipcode(50001);
        userPrevio.update(em);
        
        User userActual = User.findById(em, userPrevio.getId());
        assertEquals("login1",userActual.getLogin());
        assertEquals(getSha("passwd1"),userActual.getPasswd());
        assertEquals("Nombre 1",userActual.getFullName());
        assertEquals("Direccion 1",userActual.getAddress());
        assertEquals("Ciudad 1",userActual.getCity());
        assertEquals("Pais 1",userActual.getCountry());
        assertEquals("email1@hotmail.com",userActual.getEmail());
        assertEquals(50001,userActual.getZipcode());
        
    }
    
    @Test
    public void delete_test() {
        createAllUsers();
        
        assertEquals(user5,User.findById(em, user5.getId()));
        user5.remove(em);
        assertEquals(null,User.findById(em, user5.getId()));
        
    }
}