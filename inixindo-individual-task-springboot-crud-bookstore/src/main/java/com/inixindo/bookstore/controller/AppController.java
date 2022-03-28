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
import com.inixindo.bookstore.service.ProductService;

@Controller
public class AppController {
	@Autowired
	ProductService service;
    @RequestMapping(value = "/")
    public String viewHomePage(Model model) {
        List<Books> listProducts = service.listAll();
        model.addAttribute("listProducts", listProducts);
         
        return "index";
    }
    
    @RequestMapping("/new")
    public String showNewProductPage(Model model) {
        Books books = new Books();
        model.addAttribute("books", books);
         
        return "new_product";
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("books") Books books) {
        service.save(books);
         
        return "redirect:/";
    }
    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_product");
        Books books = service.get(id);
        mav.addObject("books", books);
         
        return mav;
    }
    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "redirect:/";         
    }
}