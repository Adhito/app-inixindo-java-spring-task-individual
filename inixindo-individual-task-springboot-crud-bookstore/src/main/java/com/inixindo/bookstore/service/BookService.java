package com.inixindo.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inixindo.bookstore.model.Books;
import com.inixindo.bookstore.repository.BookRepository;
 
public interface BookService {
 
     
    public List<Books> listAll();
    public void save(Books books);
    public Books get(long id);
    public void delete(long id);
    Page<Books> findPaginated(int pageNo, int pageSize);
    
    
}