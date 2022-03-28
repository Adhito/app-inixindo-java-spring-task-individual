package com.inixindo.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inixindo.bookstore.model.Books;

public interface ProductRepository extends JpaRepository<Books, Long> {
 
}