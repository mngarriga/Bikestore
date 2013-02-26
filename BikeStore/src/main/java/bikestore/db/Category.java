package bikestore.db;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="categories")
public class Category implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    @Column(unique=true, nullable=false, length=100)
    private String name;

    @OneToOne
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
    
    
}
