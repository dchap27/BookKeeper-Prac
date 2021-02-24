package com.room.ps.bookkeeper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Book.class}, version = 3)
@TypeConverters(DateTypeConverter.class)
public abstract class BookRoomDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
    private static final String DB_NAME = "book_database";
    private static BookRoomDatabase bookRookinstance;

    static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE books "
            + " ADD COLUMN description TEXT DEFAULT 'Add description' ");
        }
    };

    static Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE books "
                    + " ADD COLUMN last_updated INTEGER DEFAULT NULL ");
        }
    };

    public static synchronized BookRoomDatabase getInstance(Context context){
        if(bookRookinstance == null){
            bookRookinstance = Room.databaseBuilder(context.getApplicationContext(), BookRoomDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build();
        }
        return bookRookinstance;
    }
}
