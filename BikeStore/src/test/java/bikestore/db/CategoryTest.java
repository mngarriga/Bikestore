package bikestore.db;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class CategoryTest {

    private EntityManager em;
    
    //Categorias y Subcategorias
    private static Category cBici;
    private static Category cAcce;
    private static Category cRopa;
    private static Category cBMX;
    private static Category cCullote;
    private static Category cMaillot;
    private static Category cVTT;

    @Before
    public void beforeTest() throws SQLException, ParseException {
        String url = "jdbc:mysql://127.0.0.1/bikestore";
        String user = "root";
        String pass = "password";
        Connection conn = DriverManager.getConnection(url, user, pass);

        Statement stmt = conn.createStatement();
        stmt.execute("TRUNCATE TABLE categories");
        stmt.close();
        conn.close();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bikestorePU");
        em = emf.createEntityManager();

        cBici = new Category("Bicicleta", null);
        cAcce = new Category("Accesorio", null);
        cRopa = new Category("Ropa", null);
        cBMX = new Category("BMX", cBici);
        cVTT = new Category("VTT", cBici);
        cCullote = new Category();
        cCullote.setName("Cullote");
        cMaillot = new Category();
        cMaillot.setName("Maillot");
    }

    @After
    public void afterTest() {
        em.close();
    }
    
    @Test
    public void test_findById(){
        cBici.create(em);
        
        Category res = Category.findById(em, cBici.getId());
        assertEquals(res, cBici);
    }
    
    @Test
    public void test_findAll(){
        cBici.create(em);
        cAcce.create(em);
        cRopa.create(em);
        cBMX.create(em);
        cVTT.create(em);
        cCullote.create(em);
        cMaillot.create(em);
        
        List<Category> expected = new ArrayList<Category>();
        expected.add(cBici);
        expected.add(cAcce);
        expected.add(cRopa);
        expected.add(cBMX);
        expected.add(cVTT);
        expected.add(cCullote);
        expected.add(cMaillot);
        
        List<Category> lsCategories = Category.findAll(em);
        assertEquals(expected, lsCategories);
        assertEquals(7, lsCategories.size());
    }
    
    @Test
    public void test_findByPage(){
        //page 1
        cBici.create(em);
        cAcce.create(em);
        cRopa.create(em);
        //page 2
        cBMX.create(em);
        cVTT.create(em);
        cCullote.create(em);
        //page 3
        cMaillot.create(em);
        
        List<Category> expected = new ArrayList<Category>();
        expected.add(cBMX);
        expected.add(cVTT);
        expected.add(cCullote);
        
        List<Category> res = Category.findByPage(em,2,3);
        assertEquals(expected, res);
    }
   
    @Test
    public void test_findByName() {
        cAcce.create(em);
        
        Category res = Category.findByName(em, "Accesorio");
        assertEquals(cAcce, res);
    }

    @Test
    public void test_findSubCategories() {
        cBici.create(em);
        cBMX.create(em);
        cVTT.create(em);
        
        List<Category> expected = new ArrayList<Category>();
        expected.add(cBMX);
        expected.add(cVTT);
        
        em.refresh(cBici);
        //cBici = Category.findById(em, cBici.getId());
        
        List<Category> res = Category.findSubCategories(em, cBici);
        assertEquals(expected, res);
        assertEquals(expected, cBici.getSubCategories());
        
        cRopa.create(em);
        cRopa.getSubCategories().add(cCullote);
        cRopa.getSubCategories().add(cMaillot);
        
        em.getTransaction().begin();
        em.flush();
        em.getTransaction().commit();
        
        res = Category.findSubCategories(em, cRopa);
        
        expected = new ArrayList<Category>();
        expected.add(cCullote);
        expected.add(cMaillot);
        
        assertEquals(expected, res);
        assertEquals(expected, cRopa.getSubCategories());
    }    
    
    @Test
    public void test_create(){
         boolean res = cBici.create(em);
         assertTrue(res);
         
         res = cBici.create(em);
         assertFalse(res);
    }
    
    @Test
    public void test_remove(){
        cBici.create(em);
        cBMX.create(em);
        cVTT.create(em);
        
        em.refresh(cBici);
        cBici = Category.findById(em, cBici.getId());
        
        boolean res = cBici.remove(em);
        
        assertTrue(res);
        List<Category> lsCategories = Category.findAll(em);
        assertEquals(0, lsCategories.size());
    }
            
    @Test
    public void test_update(){
        cBici.create(em);
        
        cBMX.create(em);
        cVTT.create(em);
        
        cBMX.setName("BMX plus");
        Category res = cBMX.update(em);
        assertEquals(cBMX, res);
    }
}
