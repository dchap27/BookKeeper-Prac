package com.room.ps.bookkeeper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class}, version = 1)
public abstract class BookRoomDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
    private static final String DB_NAME = "book_database";
    private static BookRoomDatabase bookRookinstance;

    public static synchronized BookRoomDatabase getInstance(Context context){
        if(bookRookinstance == null){
            bookRookinstance = Room.databaseBuilder(context.getApplicationContext(), BookRoomDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return bookRookinstance;
    }
}
