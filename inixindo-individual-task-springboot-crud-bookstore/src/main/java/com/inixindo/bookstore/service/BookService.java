package com.inixindo.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inixindo.bookstore.model.Books;
import com.inixindo.bookstore.repository.BookRepository;
 
@Service
@Transactional
public class BookService {
 
    @Autowired
    private BookRepository repo;
     
    public List<Books> listAll() {
        return repo.findAll();
    }
     
    public void save(Books books) {
        repo.save(books);
    }
     
    public Books get(long id) {
        return repo.findById(id).get();
    }
     
    public void delete(long id) {
        repo.deleteById(id);
    }
}