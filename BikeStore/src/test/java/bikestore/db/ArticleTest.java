package bikestore.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class ArticleTest{
    
    private EntityManager em;
    private static Category cBici;  
    private static Category cMountainBike;
    private static Article aMB01;
    private static Article aMB02;
    private static Article aMB03;
    
    private static Category cRopa;
    private static Category cCullote;
    private static Article aCull01;
    private static Article aCull02;
    private static Article aCull03;
    
    @Before
    public void beforeTest() throws SQLException, ParseException {
        String url = "jdbc:mysql://127.0.0.1/bikestore";
        String user = "root";
        String pass = "password";
        Connection conn = DriverManager.getConnection(url, user, pass);

        Statement stmt1 = conn.createStatement();
        stmt1.execute("TRUNCATE TABLE articles");
        stmt1.close();
        
        Statement stmt2 = conn.createStatement();
        stmt2.execute("TRUNCATE TABLE categories");
        stmt2.close();
        
        conn.close();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bikestorePU");
        em = emf.createEntityManager();
        
        cBici = new Category("Bicicletas", null);
        cMountainBike = new Category("Mountain Bike", cBici);
        cRopa = new Category("Ropa", null);
        cCullote = new Category("Cullotes", cRopa);
        
        aMB01= new Article("Scott","Spark900SL", "Ruedas grandes, poco peso",cMountainBike,625,5);
        aMB02= new Article("BTWin","Rocket1200", "Bici normalita de decathlon",cMountainBike,235,10);
        aMB03= new Article("Scott","Spark930", "Perfecta para marathones, carreras por etapas",cMountainBike,750,5);
        
        aCull01 = new Article("Nike","WinterRed", "Cullote rojo calentito para invierno",cCullote,35,10);
        aCull02 = new Article("Adidas","ProCycling", "Cullote azul alta resistencia",cCullote,50,20);
        aCull03 = new Article("Nike","WinterBlue", "Cullote azul calentito con borreguillo",cCullote,45,24);
        
    }
    
    @After
    public void afterTest(){
        em.close();
    }
    
    // Test de metodos de busqueda ---------------------------------------------
    
    @Test
    public void test_findById() throws SQLException, Exception{
        cBici.create(em);
        cMountainBike.create(em);
        aMB01.create(em);
        aMB02.create(em);
        aMB03.create(em);
        Article article = Article.findById(em,2);
        Assert.assertEquals(aMB02,article);
    }
    
    @Test
    public void test_findByBrand() throws Exception{
        cBici.create(em);
        cMountainBike.create(em);
        aMB01.create(em);
        aMB02.create(em);
        aMB03.create(em);
        
        List<Article> expected = new ArrayList<Article>();
        expected.add(aMB01);
        expected.add(aMB03);
        
        List<Article> articles = Article.findByBrand(em,"Scott");
        assertEquals(expected,articles);
        
    }
    
    @Test
    public void test_findByBrandModel() throws Exception {
        cBici.create(em);
        cMountainBike.create(em);
        aMB01.create(em);
        
        Article result = Article.findByBrandModel(em, aMB01.getBrand(),aMB01.getModel());
        assertEquals(aMB01, result);
    }
    
    @Test
    public void test_findByPrice() throws Exception{
        cBici.create(em);
        cMountainBike.create(em);
        aMB01.create(em);
        aMB02.create(em);
        aMB03.create(em);
        
        List<Article> expected = new ArrayList<Article>();
        expected.add(aMB01);
        
        List<Article> articles = Article.findByPrice(em, 625);
        assertEquals(expected,articles);
    }
    
    @Test
    public void test_findAll() throws Exception {
        cBici.create(em);
        cMountainBike.create(em);
        aMB01.create(em);
        aMB02.create(em);
        aMB03.create(em);
        
        List<Article> expected = new ArrayList<Article>();
        expected.add(aMB01);
        expected.add(aMB02);
        expected.add(aMB03);

        List<Article> articles = Article.findAll(em); 
        assertEquals(expected,articles);        
    }
    
    @Test
    public void test_findByPage() throws SQLException, Exception{
        cBici.create(em);
        cMountainBike.create(em);
        aMB01.create(em);
        aMB02.create(em);
        aMB03.create(em);
        
        List<Article> expected = new ArrayList<Article>();
        expected.add(aMB01);
        expected.add(aMB02);
        
        List<Article> articles = Article.findByPage(em, 2, 2);        
        Assert.assertEquals(expected,articles);
    }
    
    @Test
    public void test_findByRangePrice() throws Exception{
        cBici.create(em);
        cMountainBike.create(em);
        aMB01.create(em);   //vale 625
        aMB02.create(em);   //vale 235
        aMB03.create(em);   //vale 750
        
        List<Article> expected = new ArrayList<Article>();
        expected.add(aMB01);
        expected.add(aMB03);
        
        List<Article> articles = Article.findByRangePrice(em,600,800);
        assertEquals(expected,articles);
    }
    
    @Test
    public void test_findByPriceLessThan() throws Exception{
        cBici.create(em);
        cMountainBike.create(em);
        aMB01.create(em);   //vale 625
        aMB02.create(em);   //vale 235
        aMB03.create(em);   //vale 750
        
        List<Article> expected = new ArrayList<Article>();
        expected.add(aMB01);
        expected.add(aMB02);
        
        List<Article> articles = Article.findByPriceLessThan(em,650);
        assertEquals(expected,articles);
    }
    
   // Test de metodos de modificacion------------------------------------------
    
   @Test
    public void test_create() throws SQLException, Exception {
        cBici.create(em);
        cMountainBike.create(em);
        aMB01.create(em);
        
        Article article = Article.findById(em, 1);
        Assert.assertEquals(aMB01, article);
    }
    
   @Test
    public void test_remove() throws SQLException, Exception{
        cBici.create(em);
        cMountainBike.create(em);
        aMB01.create(em);
        aMB02.create(em);
        
        aMB02.remove(em);
        
        Article article = Article.findById(em,aMB02.getId()); 
        Assert.assertNull(article);
    }
    
   @Test
    public void test_update() throws SQLException, Exception{
        cBici.create(em);
        cMountainBike.create(em);
        aMB01.create(em);
        
        aMB01.setBrand("Lotus");
        aMB01.update(em);
        
        Article article = Article.findById(em,aMB01.getId()); 
        assertEquals(aMB01.getBrand(),article.getBrand());
    }
    
    
}//class