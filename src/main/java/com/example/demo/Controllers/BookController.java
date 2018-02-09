package com.example.demo.Controllers;

import com.cloudinary.utils.ObjectUtils;
import com.example.demo.Models.Book;
import com.example.demo.Models.CloudinaryConfig;
import com.example.demo.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class BookController {
    @Autowired
    BookRepository bookRepository;

    final String bookPath = "/books";

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping(bookPath)
    public String listBooks(Model model){
        //test
        /*
        Book borrowedBook = new Book();
        borrowedBook.setBorrowed(true);
        borrowedBook.setAuthor("borrowed book");
        borrowedBook.setIsbn("123");
        borrowedBook.setTitle("title of boorred");
        borrowedBook.setYearOfPublication("2018");

        Book book = new Book();
        book.setAuthor("unborrwoed");
        book.setIsbn("789");
        book.setTitle("unbor");
        book.setYearOfPublication("2003");

        bookRepository.save(book);
        bookRepository.save(borrowedBook);
        */

        model.addAttribute("books", bookRepository.findAll());
        return bookPath + "/list";
    }

    @RequestMapping(bookPath + "/available")
    public String listAvailableBooks(Model model){
        model.addAttribute("books", bookRepository.findAllByIsBorrowed(false));
        return bookPath + "/borrowList";
    }

    @RequestMapping(bookPath + "/unavailable")
    public String listUnavailableBooks(Model model){
        model.addAttribute("books", bookRepository.findAllByIsBorrowed(true));
        return bookPath + "/returnList";
    }

    @RequestMapping(bookPath + "/available/{id}")
    public String msglistAvailableBooks(@PathVariable("id") long id, Model model){
        model.addAttribute("borrowedBook", bookRepository.findOne(id));
        model.addAttribute("books", bookRepository.findAllByIsBorrowed(false));
        return bookPath + "/msgBorrowList";
    }

    @RequestMapping(bookPath + "/unavailable/{id}")
    public String msglistUnavailableBooks(@PathVariable("id") long id, Model model){
        model.addAttribute("returnedBook", bookRepository.findOne(id));
        model.addAttribute("books", bookRepository.findAllByIsBorrowed(true));
        return bookPath + "/msgReturnList";
    }


    @GetMapping(bookPath + "/add")
    public String addBook(Model model){
        model.addAttribute("book", new Book());
        return bookPath + "/form";
    }

    @PostMapping(bookPath + "/process")
    public String processBook(@Valid Book book, BindingResult result, @RequestParam("file")MultipartFile file){
        if(result.hasErrors()){
            return bookPath + "/form";
        }
        if(!file.isEmpty()){
            try{
                Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                book.setImage(uploadResult.get("url").toString());
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        bookRepository.save(book);
        return "redirect:" + bookPath;
    }


    @RequestMapping(bookPath + "/borrow/{id}")
    public String borrowBookPage(@PathVariable("id") long id, Model model){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date().getTime());
        Book book = bookRepository.findOne(id);
        book.setBorrowed(true);
        book.setLastBorrowed(timeStamp);
        ArrayList<String> temp = book.getBorrowHistory();
        temp.add(timeStamp);
        book.setBorrowHistory(temp);
        book.setNumBorrowed(temp.size());
        bookRepository.save(book);

        return "redirect:" + bookPath + "/available/" + book.getId();
    }

    @RequestMapping(bookPath + "/return/{id}")
    public String returnBookPage(@PathVariable("id") long id, Model model){
        Book book = bookRepository.findOne(id);
        book.setBorrowed(false);
        bookRepository.save(book);
        return "redirect:" + bookPath + "/unavailable/" + book.getId();
    }

    @RequestMapping(bookPath + "/detail/{id}")
    public String detailBook(@PathVariable("id") long id, Model model){
        model.addAttribute("book", bookRepository.findOne(id));
        return bookPath + "/detail";
    }

    @RequestMapping(bookPath + "/popular")
    public String popularBook(Model model){
        model.addAttribute("books", bookRepository.findAllByOrderByNumBorrowedDesc());
        return bookPath + "/popular";
    }
}
