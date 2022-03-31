package com.inixindo.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inixindo.bookstore.model.Books;

public interface BookRepository extends JpaRepository<Books, Long> {

	
	@Query(value="SELECT * FROM Books b WHERE b.name LIKE %:keyword% OR b.publisher LIKE %:keyword%", nativeQuery=true)
    public List<Books> findByKeyword(@Param("keyword") String keyword);
 
}