package com.inixindo.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.inixindo.bookstore.model.Books;
import com.inixindo.bookstore.service.BookService;

@Controller
public class AppController {
	@Autowired
	BookService service;
    @RequestMapping(value = "/")
    public String viewHomePage(Model model) {
        List<Books> listBooks = service.listAll();
        model.addAttribute("listBooks", listBooks);
         
        return "index";
    }
    
    @RequestMapping("/new")
    public String showNewBookPage(Model model) {
        Books books = new Books();
        model.addAttribute("books", books);
         
        return "add_book";
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveBook(@ModelAttribute("books") Books books) {
        service.save(books);
         
        return "redirect:/";
    }
    
    @RequestMapping("/edit/{id}")
    public ModelAndView showEditBookPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_book");
        Books books = service.get(id);
        System.out.println(books);
        mav.addObject("books", books);
         
        return mav;
    }
    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "redirect:/";         
    }
}