package BookStoreAssignmentAniketSharma.assignment3.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import BookStoreAssignmentAniketSharma.assignment3.beans.Books;
import BookStoreAssignmentAniketSharma.assignment3.beans.User;
import BookStoreAssignmentAniketSharma.assignment3.database.DatabaseAccess;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    @Lazy
    DatabaseAccess da;
	

	@GetMapping("/secure")
	public String secureIndex(Model model) {
	    model.addAttribute("book", new Books());
		return "/secure/index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String postLogin(Model model) {
	    model.addAttribute("book", new Books());
	    model.addAttribute("bookList", da.getBook());
	    return "/secure/browse";
	}
	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}
	@GetMapping("/register")
	public String getRegister () {
	return "register";
	}
	
	@PostMapping("/secure/addToCart/{id}")
	public String addToCart(@PathVariable Long id, HttpSession session, Model model) {
	    // Retrieve the book from the database
	    Books book = da.getBookById(id).get(0);
	    System.out.println("Adding book to cart - Title: " + book.getBookName() +
	            ", Author: " + book.getDescription() +
	            ", Price: " + book.getPrice());
	    // Check if the user object is in the session

	    List<Books> cart = (List<Books>) session.getAttribute("cart");
	    if (cart == null) {
	        cart = new ArrayList<>();
	        session.setAttribute("cart", cart);
	    }

	    // Check if the book is already in the cart
	    boolean bookExistsInCart = false;
	    for (Books cartBook : cart) {
	        if (cartBook.getId().equals(id)) {
	            cartBook.setQuantity(cartBook.getQuantity() + 1);
	            bookExistsInCart = true;
	            break;
	        }
	    }

	    // If the book is not in the cart, add it with quantity 1
	    if (!bookExistsInCart) {
	        book.setQuantity(1);
	        cart.add(book);
	    }

	    // Update the cart in the session
	    session.setAttribute("cart", cart);
	    model.addAttribute("cart", cart);
	    model.addAttribute("book", book);
	    model.addAttribute("bookList", da.getBookById(id));
	    return "/secure/viewCart";
	}

	@GetMapping("/secure/viewCart")
	public String viewCart(Model model, HttpSession session, Books book) {
	    List<Books> cart = (List<Books>) session.getAttribute("cart");
	    model.addAttribute("cart", cart);
        model.addAttribute("book", book);
	    return "/secure/viewCart";
	}

	@PostMapping("/register")
	public String postRegister(Model model, @RequestParam String username, @RequestParam String password) {
	    da.addUser(username, password);
	    Long userId = da.findUserAccount(username).getUserId();
	    da.addRole(userId, Long.valueOf(1));
	    model.addAttribute("book", new Books());
	    model.addAttribute("bookList", da.getBook());
	    return "/secure/browse";
	}
	
	@GetMapping("/updateQuantity/{id}")
	public String updateQuantity(@PathVariable Long id, @RequestParam int quantity, HttpSession session) {
	    List<Books> cart = (List<Books>) session.getAttribute("cart");
	    for (Books book : cart) {
	        if (book.getId().equals(id)) {
	            book.setQuantity(quantity);
	            break;
	        }
	    }
	    session.setAttribute("cart", cart);
	    return "redirect:/viewCart";
	}

	
	@PostMapping("/secure/orderconfirmation")
	public String placeOrder(@RequestParam Long bookId, HttpSession session, Model model) {
	    // Retrieve the book from the shopping cart using the bookId
	    List<Books> cart = (List<Books>) session.getAttribute("cart");
	    Books book = cart.stream().filter(cartBook -> cartBook.getId().equals(bookId)).findFirst().orElse(null);

	    if (book != null) {
	        // Save order details to the database
	        da.saveOrderDetails(book);

	        // Remove the book from the cart (optional, based on your business logic)
	        cart.removeIf(cartBook -> cartBook.getId().equals(bookId));
	    }

	    // Add the order details to the model for displaying on the confirmation page
	    List<Books> orderDetails = new ArrayList<>();
	    orderDetails.add(book);
	    model.addAttribute("orderDetails", orderDetails);

	    return "/secure/orderconfirmation";
	}


	
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/secure/books")
    public String secureBooks(Model model) {
        model.addAttribute("book", new Books());
        model.addAttribute("bookList", da.getBook());
        return "/secure/browse"; 
    }
    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("book", new Books());
        model.addAttribute("bookList", da.getBook());
        return "/secure/browse"; 
    }

    @PostMapping("/secure/insertBook")
    public String insertBook(Model model, @ModelAttribute Books book) {
        List<Books> bookList = da.getBookById(book.getId());
        if (bookList.isEmpty()) {
            da.insertBook(book);
        } else {
            da.updateBook(book);
        }
        model.addAttribute("bookList", da.getBook());
        model.addAttribute("book", new Books());

        return "/secure/browse"; 
    }

    @GetMapping("/secure/deleteBook/{id}")
    public String deleteBook(Model model, @PathVariable Long id) {
        da.deleteBookById(id);
        model.addAttribute("bookList", da.getBook());
        model.addAttribute("book", new Books());

        return "/secure/browse"; // Redirect to the browse page after delete
    }

    @GetMapping("/secure/editBook/{id}")
    public String editBook(Model model, @PathVariable Long id) {
        Books book = da.getBookById(id).get(0);

        model.addAttribute("bookList", da.getBook());
        model.addAttribute("book", book);
        return "/secure/insert"; // Updated to point to the edit.html Thymeleaf template
    }
}
