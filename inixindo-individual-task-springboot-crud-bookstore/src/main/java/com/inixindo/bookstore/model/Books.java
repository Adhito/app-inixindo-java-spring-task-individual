package com.inixindo.bookstore.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 



/*@Entity (name="books")
@Data
*/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Books {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "name")
    @NotEmpty(message = "*Please provide a book name")
    private String name;

    @Column(name = "publisher")
    @NotEmpty(message = "*Please provide a publisher name")
    private String publisher;
    
    @Column(name = "publish_date")
    @NotEmpty(message = "*Please provide a book published date name (YYYY)")
    private String publish_date;
    
    @Column(name = "genre")
    @NotEmpty(message = "*Please provide a publisher genre")
    private String genre;
    
    private float price;
 
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
     
    // other getters and setters are hidden for brevity
}