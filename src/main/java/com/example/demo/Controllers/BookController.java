package com.example.demo.Controllers;

import com.example.demo.Models.Book;
import com.example.demo.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BookController {
    @Autowired
    BookRepository bookRepository;
    final String bookPath = "/books";

    @RequestMapping(bookPath)
    public String listBooks(Model model){
        model.addAttribute("books", bookRepository.findAll());
        return bookPath + "/list";
    }

    @RequestMapping(bookPath + "/add")
    public String addBook(Model model){
        model.addAttribute("book", new Book());
    }
}
