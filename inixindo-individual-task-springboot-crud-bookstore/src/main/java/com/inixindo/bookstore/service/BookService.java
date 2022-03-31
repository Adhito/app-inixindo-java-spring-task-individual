package com.inixindo.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inixindo.bookstore.model.Books;
import com.inixindo.bookstore.repository.BookRepository;

@Service
@Transactional
public class BookService{
 
    @Autowired
    private BookRepository bookRepository;

	public List<Books> listAll() {
		// TODO Auto-generated method stub
		return bookRepository.findAll();
	}

	public void save(Books books) {
		// TODO Auto-generated method stub
		bookRepository.save(books);
		
	}

	public Books get(long id) {
		// TODO Auto-generated method stub
		return bookRepository.findById(id).get();
	}

	public void delete(long id) {
		// TODO Auto-generated method stub
		bookRepository.deleteById(id);
		
	}
	
	public Page<Books> listAll(int pageNum) {
	    int pageSize = 8;
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
	    return bookRepository.findAll(pageable);
	}
	
	
	
	

	public Page<Books> findPaginated(int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return this.bookRepository.findAll(pageable);
	}

	public List<Books> findByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return this.bookRepository.findByKeyword(keyword);
	}
     
//    public List<Books> listAll() {
//        return bookRepository.findAll();
//    }
//     
//    public void save(Books books) {
//        bookRepository.save(books);
//    }
//     
//    public Books get(long id) {
//        return bookRepository.findById(id).get();
//    }
//     
//    public void delete(long id) {
//        bookRepository.deleteById(id);
//    }
	
	
}