package com.room.ps.bookkeeper;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements BookListAdapter.OnDeleteClickListener {

    public static final int UPDATE_BOOK_ACTIVITY_REQUEST_CODE = 2;
    private static final int NEW_BOOK_ACTIVITY_REQUEST_CODE = 1;
    public static final String NEW_AUTHOR = "com.room.ps.bookkeeper.NEW_AUTHOR";
    public static final String NEW_BOOK = "com.room.ps.bookkeeper.NEW_BOOK";
    public static final String NEW_DESCRIPTION = "com.room.ps.bookkeeper.NEW_DESCRIPTION";
    private BookViewModel mBookViewModel;
    private RecyclerView mRecyclerView;
    private BookListAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerAdapter = new BookListAdapter(this, this);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mBookViewModel.allBooks.observe(this, books -> {
            if(books != null){
                mRecyclerAdapter.setBooks(books);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewBookActivity.class);
                startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){

            String id = UUID.randomUUID().toString();
            String authorName = data.getStringExtra(NEW_AUTHOR);
            String bookName = data.getStringExtra(NEW_BOOK);
            String description = data.getStringExtra(NEW_DESCRIPTION);

            Book book = new Book(id, authorName, bookName, description);

            mBookViewModel.insert(book);
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
        } else if (requestCode == UPDATE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String id = data.getStringExtra(EditBookActivity.ID);
            String authorName = data.getStringExtra(EditBookActivity.UPDATED_AUTHOR);
            String bookName = data.getStringExtra(EditBookActivity.UPDATED_BOOK);
            String description = data.getStringExtra(EditBookActivity.UPDATED_DESCRIPTION);

            Book book = new Book(id, authorName, bookName, description);
            mBookViewModel.update(book);

            Toast.makeText(this, R.string.updated, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, R.string.not_saved, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Get the search view and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        // Setting the SearchResultActivity to show the result
        ComponentName componentName = new ComponentName(this, SearchResultActivity.class);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(componentName);
        searchView.setSearchableInfo(searchableInfo);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onDeleteClickListener(Book myBook) {
        mBookViewModel.delete(myBook);
        Toast.makeText(this, R.string.deleted, Toast.LENGTH_SHORT).show();
    }
}