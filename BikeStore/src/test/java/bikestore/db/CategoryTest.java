package bikestore.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
        cCullote = new Category("Cullote", cRopa);
        cMaillot = new Category("Maillot", cRopa);
    }

    @After
    public void afterTest() {
        em.close();
    }
    
    @Test
   // @Ignore
    public void test_findById(){
        cBici.create(em);
        
        Category res = Category.findById(em, cBici.getId());
        assertEquals(res, cBici);
    }
    
}
