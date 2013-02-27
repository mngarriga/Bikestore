package bikestore.db;

import java.io.*;
import javax.persistence.*;
import java.security.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@Table(name="users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String login;
    private String passwd;
    private String fullName;
    private String email;
    private String address;
    private String city;
    private String country;
    private int zipcode;

    public User(String login, String passwd, String fullName, String email, String address, String city, String country, int zipcode) {
        this.login = login;
//        setPasswd(passwd);
        this.passwd = getSha(passwd);
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.city = city;
        this.country = country;
        this.zipcode = zipcode;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = getSha(passwd);
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final User other = (User) obj;
        if (this.id != other.id && this.login != other.login) {
            return false;
        }
        return true;
    }

    //------------------
    // Active Record
    //------------------

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
//            throw new Exception("Error saving user");
            e.printStackTrace();
            return false;
        }
    }
    
    public void createNoTransaction(EntityManager em) {
        em.persist(this);
        em.flush();
    }
    
    public User update(EntityManager em) {
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            User user = updateNoTransaction(em);
            et.commit();
            return user;
        } catch (Exception e) {
            if (et.isActive()) {
                et.rollback();
            }
            return null;
        }
    }

    public User updateNoTransaction(EntityManager em) {
        User user = em.merge(this); // No utilizar em.refresh(this), no es correcto.
        em.flush();
        return user;
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
//            throw new Exception("Error saving user");
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeNoTransaction(EntityManager em) {
        if (em.find(User.class, this.getId()) != null) {
            em.remove(this);
            em.flush();
            return true;
        } else {
            return false;
        }
    }
    
    public static long count(EntityManager em) {
        String eql = "SELECT COUNT(u) FROM User u;";
        TypedQuery<Long> query = em.createQuery(eql,Long.class);
        return query.getSingleResult();
    }

    public static User findById(EntityManager em,long id) {
        return em.find(User.class, id);
    }
    
    public static List<User> findAll(EntityManager em) {
        String sql = "SELECT u FROM User u ORDER BY u.id";
        TypedQuery<User> q = em.createQuery(sql, User.class);
        return q.getResultList();
    }
    
    public static List<User> findByPage(EntityManager em,int page,int usersPerPage) {
        String sql = "SELECT u FROM User u ORDER BY u.id";
        TypedQuery<User> q = em.createQuery(sql, User.class);
        q.setFirstResult((page - 1) * usersPerPage);
        q.setMaxResults(usersPerPage);
        return q.getResultList();
    }
    
    
    public static boolean exists(EntityManager em, String login) {
        String sql = "SELECT u FROM User u WHERE u.login = :login";
        TypedQuery<User> q = em.createQuery(sql,User.class);
        q.setParameter("login", login);
        try {
            User user = q.getSingleResult();
            return login.equals(user.getLogin());
        } catch (Exception ex)
        {
            return false;        
        }
        finally {
        }
    }
    
    public static User loginUser(EntityManager em, String login, String passwd) {
        String sql = "SELECT u FROM User u WHERE u.login = :login";
        TypedQuery<User> q = em.createQuery(sql,User.class);
        q.setParameter("login", login);
        try {
            User user = q.getSingleResult();
            
            // check user
            if (user != null && login.equals(user.getLogin())) {
                // check passwd
                if(user.getPasswd().equals(getSha(passwd))) {
                    return user;
                }
            }
            return null;
        }
        catch (NoResultException ex)
        {
            return null;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}