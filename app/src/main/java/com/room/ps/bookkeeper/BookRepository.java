package com.room.ps.bookkeeper;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookRepository {
    private BookRoomDatabase mBookRoomDatabase;
    private BookDao mBookDao;
    public LiveData<List<Book>> allBooks;

    public BookRepository(Application application){
        mBookRoomDatabase = BookRoomDatabase.getInstance(application);
        mBookDao = mBookRoomDatabase.bookDao();
        allBooks = mBookDao.getAllBooks();
    }

    public void insert(Book book){
        new InsertAsyncTask(mBookDao).execute(book);
    }

    public void update(Book book){
        new UpdateAsyncTask(mBookDao).execute(book);
    }

    public void delete(Book book){
        new DeleteAsyncTask(mBookDao).execute(book);
    }

    public LiveData<List<Book>> getBooksByBookOrAuthor(String searchQuery){
        return mBookDao.getBooksByBookOrAuthor(searchQuery);
    }

    private static class InsertAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDao mBookDao;

        public InsertAsyncTask(BookDao bookDao){
            this.mBookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            mBookDao.insert(books[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Book, Void, Void> {
        private final BookDao mBookDao;

        public UpdateAsyncTask(BookDao bookDao) {
            this.mBookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            mBookDao.update(books[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Book, Void, Void> {
        private final BookDao mBookDao;

        public DeleteAsyncTask(BookDao bookDao) {
            this.mBookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            mBookDao.delete(books[0]);
            return null;
        }
    }


}
