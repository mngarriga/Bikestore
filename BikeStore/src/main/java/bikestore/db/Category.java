package bikestore.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="categories")
public class Category implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    @Column(unique=true, nullable=false, length=100)
    private String name;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="id_cat_superior", referencedColumnName="id")
    private Category catSup;

    
    public Category() {
    }

    public Category(String name, Category catSup) {
        this.name = name;
        this.catSup = catSup;
    }

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

    public Category getCatSup() {
        return catSup;
    }

    public void setCatSup(Category catSup) {
        this.catSup = catSup;
    }

    @Override
    public String toString() {
        return "name=" + name + '}';
    }

    //HasCode & Equals
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final Category other = (Category) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
    
    //
    // Active Record
    //

    // Queries
    public static List<Category> findAll(EntityManager em) {
        String sql = "SELECT c FROM Category c ORDER BY c.id";
        TypedQuery<Category> query = em.createQuery(sql, Category.class);
        return query.getResultList();
    }

    public static List<Category> findByPage(EntityManager em, int page, int usersPerPage) {

        String sql = "SELECT c FROM Category c ORDER BY c.id";
        TypedQuery<Category> query = em.createQuery(sql, Category.class);
        query.setFirstResult((page - 1) * usersPerPage);
        query.setMaxResults(usersPerPage);
        return query.getResultList();
    }    
    
    public static Category findById(EntityManager em, long id) {
        return em.find(Category.class, id);
    }

    public static Category findByName(EntityManager em, String name) {
        String sql = "SELECT c FROM Category c WHERE c.name = :name";
        TypedQuery<Category> query = em.createQuery(sql, Category.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public static List<Category> findSubCategories(EntityManager em, Category catSup) {
        String sql = "SELECT c FROM Category c WHERE c.catSup = :catSup";
        TypedQuery<Category> query = em.createQuery(sql, Category.class);
        query.setParameter("catSup", catSup);
        return query.getResultList();
    }
    
    // Modifying

    public boolean create(EntityManager em) {
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
            return false;
        }
    }

    public boolean removeNoTransaction(EntityManager em) {
        if (em.find(Category.class, this.getId()) != null) {
            em.remove(this);
            em.flush();
            return true;
        } else {
            return false;
        }
    }

    public Category update(EntityManager em) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Category cat = updateNoTransaction(em);
            et.commit();
            return cat;
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            return null;
        }
    }
    
    public Category updateNoTransaction(EntityManager em) {
        Category cat = em.merge(this);
        em.flush();
        return cat;
    }
}
