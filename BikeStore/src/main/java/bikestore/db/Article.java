package bikestore.db;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 30, nullable = false)
    private String brand;
    @Column(length = 100, nullable = false)
    private String model;
    @Column(length = 255, nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id", nullable = false)
    private Category category;
    @Column(length = 8, precision = 2, nullable = false)
    private double price;
    @Column(nullable = false)
    private int stock;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private Byte[] picture;

//  CONSTRUCTORS -----------------------------------------------------------
    public Article(String brand, String model, String description, Category idCategory, double price, int stock) {
        this.brand = brand;
        this.model = model;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
        return "Article{" + "name=" + brand + ", price=" + price + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.brand != null ? this.brand.hashCode() : 0);
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
        if ((this.brand == null) ? (other.brand != null) : !this.brand.equals(other.brand)) {
            return false;
        }
        return true;
    }

//-----------------------------------------------------------------------------
//                              ACTIVE RECORD
//-----------------------------------------------------------------------------
//Queries----------------------------------------------------------------------
    public static Article findById(EntityManager em, long id) {
        return em.find(Article.class, id);
    }

    public static List<Article> findByBrand(EntityManager em, String brand) {
        String sql = "SELECT x FROM Article x WHERE x.brand = :brand";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }
    
    public static Article findByBrandModel(EntityManager em, String brand, String model) {
        String sql = "SELECT x FROM Article x WHERE x.brand = :brand AND x.model = :model";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        query.setParameter("brand", brand);
        query.setParameter("model", model);
        return query.getSingleResult();
    }

    public static List<Article> findByPrice(EntityManager em, double price) {
        String sql = "SELECT x FROM Article x WHERE x.price = :price";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        query.setParameter("price", price);
        return query.getResultList();
    }
    
    public static List<Article> findAll(EntityManager em) {
        String sql = "SELECT x FROM Article x";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        return query.getResultList();
    }
    
    public static List<Article> findByPage(EntityManager em, int page, int articlesPerPage) {

        String sql = "SELECT x FROM Article x ORDER BY x.id";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        query.setFirstResult((page - 1) * articlesPerPage);
        query.setMaxResults(articlesPerPage);
        return query.getResultList();
    }
    
    public static List<Article> findByRangePrice(EntityManager em, double price_min, double price_max) {
        String sql = "SELECT x FROM Article x WHERE x.price>= :price_min AND x.price<=:price_max";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        query.setParameter(":price_min", price_min);
        query.setParameter(":price_max", price_max);
        return query.getResultList();
    }
    

    public static List<Article> findByPriceLessThan(EntityManager em, double price) {
        String sql = "SELECT x FROM Article x WHERE x.price<=:price";
        TypedQuery<Article> query = em.createQuery(sql, Article.class);
        query.setParameter(":price", price);
        return query.getResultList();
    }
    
  
    
//Modifying --------------------------------------------------------------------
    
    public boolean create(EntityManager em) throws Exception {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            createNoTransaction(em);
            et.commit();
            return true;
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean createNoTransaction(EntityManager em) {
        if (em.contains(this)) {
            return false;
        } else {
            em.persist(this);
            em.flush();
            return true;
        }
    }

    public boolean remove(EntityManager em) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            boolean res = removeNoTransaction(em);
            et.commit();
            return res;
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeNoTransaction(EntityManager em) {
        if (em.find(Article.class, this.getId()) != null) {
            em.remove(this);
            em.flush();
            return true;
        } else {
            return false;
        }
    }

    public Article update(EntityManager em) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Article article = updateNoTransaction(em);
            et.commit();
            return article;
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            return null;
        }
    }

    private Article updateNoTransaction(EntityManager em) {
        Article art = em.merge(this);
        em.flush();
        return art;
    }
} //class

