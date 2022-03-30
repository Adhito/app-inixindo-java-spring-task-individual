package com.inixindo.bookstore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
 
@Entity (name="books")
@Data
public class Books {
    private Long id;
    private String name;
    private String publisher;
    private String publish_date;
    private String genre;
    private float price;
 
    public Books() {
    }
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
     
    // other getters and setters are hidden for brevity
}