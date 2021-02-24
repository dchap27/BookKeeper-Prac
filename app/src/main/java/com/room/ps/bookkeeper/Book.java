package com.room.ps.bookkeeper;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {

    @NonNull
    @PrimaryKey
    private String id;

    private String author;

    private String book;

    private String description;

    public Book(String id, String author, String book, String description) {
        this.id = id;
        this.author = author;
        this.book = book;
        this.description= description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
