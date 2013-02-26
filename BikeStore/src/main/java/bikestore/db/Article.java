package bikestore.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    
    
    
    
    
    
} //class



