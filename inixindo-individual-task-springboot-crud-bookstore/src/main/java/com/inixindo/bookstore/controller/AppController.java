package com.inixindo.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.inixindo.bookstore.model.Books;
import com.inixindo.bookstore.model.User;
import com.inixindo.bookstore.service.BookService;
import com.inixindo.bookstore.service.UserService;

@Controller
public class AppController {
	@Autowired
	private UserService userService;

	@Autowired
	BookService bookService;

	@GetMapping(value = { "/", "/login" })
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@GetMapping(value = "/registration")
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@PostMapping(value = "/registration")
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByUserName(user.getUserName());
		if (userExists != null) {
			bindingResult.rejectValue("userName", "error.user",
					"There is already a user registered with the user name provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}

	@GetMapping(value = "/admin/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUserName(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " "
				+ user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

	/*
	 * Version-01 (View All Result) ============================
	 * 
	 * @RequestMapping(value = "/index") public String viewHomePage(Model model) {
	 * // ModelAndView modelAndView = new ModelAndView(); Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); List<Books> listBooks
	 * = bookService.listAll(); model.addAttribute("listBooks", listBooks); //return
	 * "index"; }
	 */

	/*
	 * Version-02 (Paginated Result) ============================
	 * 
	 * @RequestMapping(value = "/index") public String viewHomePage(Model model) {
	 * return findPaginated(1, model); }
	 * 
	 * @GetMapping("/index/page/{pageNo}") public String findPaginated(@PathVariable
	 * (value = "pageNo") int pageNo, Model model) { int pageSize = 8;
	 * 
	 * Page<Books> page = bookService.findPaginated(pageNo, pageSize); List<Books>
	 * listBooks = page.getContent();
	 * 
	 * model.addAttribute("currentPage", pageNo); System.out.println(pageNo);
	 * model.addAttribute("totalPages", page.getTotalPages());
	 * System.out.println(page.getTotalPages()); model.addAttribute("totalItems",
	 * page.getTotalElements()); System.out.println(page.getTotalElements());
	 * model.addAttribute("listBooks", listBooks); return "index"; }
	 * 
	 */

	@RequestMapping("index/page/{pageNum}")
	public String viewPage(Model model, @PathVariable(name = "pageNum") int pageNum,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {

		Page<Books> page = bookService.listAllPaginated(pageNum, sortField, sortDir);

		List<Books> listBooks = page.getContent();

		model.addAttribute("currentPage", pageNum);
		System.out.println(pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		System.out.println(page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		System.out.println(page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listBooks", listBooks);

		return "index";
	}

	@RequestMapping("/index")
	public String viewHomePage(Model model) {
		return viewPage(model, 1, "id", "asc");
	}

	@RequestMapping(value = "/home")
	public ModelAndView home(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByUserName(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " "
				+ user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");

		// List<Books> listBooks = bookService.listAll();
		// model.addAttribute("listBooks", listBooks);

		modelAndView.setViewName("/index");
		return modelAndView;

	}

	@RequestMapping("/new")
	public String showNewBookPage(Model model) {
		Books books = new Books();
		model.addAttribute("books", books);

		return "add_book";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveBook(@Valid @ModelAttribute("books") Books books, BindingResult bindingResult) {

		System.out.println(books);

		if (bindingResult.hasErrors()) {

			return "add_book";
		} else {
			bookService.save(books);
			return "redirect:/index";
		}

	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditBookPage(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("edit_book");
		Books books = bookService.get(id);
		System.out.println(books);
		mav.addObject("books", books);

		return mav;
	}

	@RequestMapping("/delete/{id}")
	public String deleteBook(@PathVariable(name = "id") int id) {
		bookService.delete(id);
		return "redirect:/index";
	}

}