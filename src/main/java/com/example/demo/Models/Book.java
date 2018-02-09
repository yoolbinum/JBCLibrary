package com.example.demo.Models;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Size(min=1)
    private String title;

    @NotNull
    @Size(min=4)
    private String author;

    @NotNull
    @Size(min = 4, max = 4)
    private String yearOfPublication;

    private String isbn;

    private String image;

    @NotNull
    private boolean isBorrowed = false;

    private ArrayList<String> borrowHistory = new ArrayList<>();

    private String lastBorrowed;

    private int numBorrowed;

    public int getNumBorrowed() {
        return numBorrowed;
    }

    public void setNumBorrowed(int numBorrowed) {
        this.numBorrowed = numBorrowed;
    }

    public ArrayList<String> getBorrowHistory() {
        return borrowHistory;
    }

    public void setBorrowHistory(ArrayList<String> borrowHistory) {
        this.borrowHistory = borrowHistory;
    }

    public String getLastBorrowed() {
        return lastBorrowed;
    }

    public void setLastBorrowed(String lastBorrowed) {
        this.lastBorrowed = lastBorrowed;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    @Required
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    @Required
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    @Required
    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
