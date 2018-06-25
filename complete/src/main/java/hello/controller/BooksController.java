package hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hello.domain.Book;
import hello.service.BookService;

@Controller
public class BooksController {

    private BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/books/all")
    public String books(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "allBooks";
    }

    @GetMapping(value = "/book/create")
    public String newBook(Model model) {
        model.addAttribute("newBook", new Book());
        return "createBook";
    }

    @PostMapping(value = "/book/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        bookService.removeBook(id);
        model.addAttribute("books", bookService.getAllBooks());
        return "allBooks";
    }

    @PostMapping(value = "/book/create")
    public String create(@ModelAttribute Book book) {
        bookService.createBook(book);
        return "redirect:/books/all";
    }
}
