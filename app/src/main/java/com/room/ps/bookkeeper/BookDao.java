package com.room.ps.bookkeeper;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface BookDao {

    @Insert
    void insert(Book book);

    @Query("SELECT * FROM books")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT * FROM books WHERE book LIKE :searchString OR author LIKE :searchString")
    LiveData<List<Book>> getBooksByBookOrAuthor(String searchString);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);
}
