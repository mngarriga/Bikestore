package bikestore.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import javax.persistence.*;
import org.junit.After;
import org.junit.Before;

public class ArticleTest{
    private EntityManager em;
    
    @Before
    public void beforeTest() throws SQLException, ParseException {
        String url = "jdbc:mysql://127.0.0.1/bikestore";
        String user = "root";
        String pass = "password";
        Connection conn = DriverManager.getConnection(url, user, pass);

        Statement stmt = conn.createStatement();
        stmt.execute("TRUNCATE TABLE articles");
        stmt.close();
        conn.close();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bikestorePU");
        em = emf.createEntityManager();
    }
    
    @After
    public void afterTest(){
        em.close();
    }
    
    
    
    
    
    
    
    
    
    
}//class