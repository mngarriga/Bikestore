package bikestore.db;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

@Entity
@Table(name = "articles")
public class Article{ 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(length = 100, unique = true, nullable = false)
    private String name;
    
    @Column(length = 255, nullable = false)
    private String description;
    
    @ManyToOne(fetch= FetchType.LAZY)  
    @JoinColumn(name ="id_category",referencedColumnName="id",nullable=false)
    private Category category;
    
    @Column(length=8,precision=2,nullable = false)
    private double price;
    
    @Column(nullable = false)
    private int stock;
    
    @Lob
    @Basic(fetch= FetchType.LAZY)
    private Byte[] picture;    

    
//  CONSTRUCTORS -----------------------------------------------------------
    
    
    public Article(String name, String description, Category idCategory, double price, int stock) {
        this.name = name;
        this.description = description;
        this.category = idCategory;
        this.price = price;
        this.stock = stock;        
    }

    public Article() {
    }

//  GETTERS & SETTERS ----------------------------------------------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
        this.picture = picture;
    }

    
// toString, hashCode, equals --------------------------------------------------
    
    @Override
    public String toString() {
        return "Article{" + "name=" + name + ", price=" + price + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }    
    
//-----------------------------------------------------------------------------
//                              ACTIVE RECORD
//-----------------------------------------------------------------------------
    
//Queries-------------------------------------------------------------------


    public static Article findById(EntityManager em, long id) {
        return em.find(Article.class, id);
    }

   public static Article findByName(EntityManager em, String name) {
        String sql = "SELECT x FROM Article x WHERE x.name = :name";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        query.setParameter("name",name);        
        return query.getSingleResult();
    }
   
   public static List<Article> findByPrice(EntityManager em, double price) {
        String sql = "SELECT x FROM Article x WHERE x.price = :price";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        query.setParameter("price",price);        
        return query.getResultList();
    }
   
   public static List<Article> findByRangePrice(EntityManager em, double price_min,double price_max) {
        String sql = "SELECT x FROM Article x WHERE x.price>= :price_min AND x.price<=:price_max";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        query.setParameter(":price_min",price_min);        
        query.setParameter(":price_max",price_max);        
        return query.getResultList();
    }
   
   public static List<Article> findByPriceLessThan(EntityManager em, double price) {
        String sql = "SELECT x FROM Article x WHERE x.price<=:price";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        query.setParameter(":price",price);                 
        return query.getResultList();
    }
   
    public static List<Article> findAll(EntityManager em) {
        String sql = "SELECT x FROM Article";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        return query.getResultList();
    }

   
    
    
    
    
    
    
    
    
    
    
} //class



