package com.room.ps.bookkeeper;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private BookRepository bookRepository;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
    }

    public void update(Book book){
        bookRepository.update(book);
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }

    public LiveData<List<Book>> getBooksByBookOrAuthor(String searchQuery){
        return bookRepository.getBooksByBookOrAuthor(searchQuery);
    }
}
