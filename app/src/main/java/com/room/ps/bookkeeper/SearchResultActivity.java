package com.room.ps.bookkeeper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SearchResultActivity extends AppCompatActivity implements BookListAdapter.OnDeleteClickListener {

    private final String TAG = getClass().getSimpleName();
    private static final int UPDATE_BOOK_ACTIVITY_REQUEST_CODE = 2;
    private SearchViewModel mSearchViewModel;
    private RecyclerView mRecyclerView;
    private BookListAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSearchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerAdapter = new BookListAdapter(this, this);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Intent intent = getIntent();
        handleIntent(intent);

    }

    private void handleIntent(Intent intent) {
        if(Intent.ACTION_SEARCH == intent.getAction()){

            String searchQuery = intent.getStringExtra(SearchManager.QUERY);
            Log.i(TAG, "Search query is "+ searchQuery);

            mSearchViewModel.getBooksByBookOrAuthor("%" + searchQuery+"%").observe(this, books -> {
                if(books != null){
                    mRecyclerAdapter.setBooks(books);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String id = data.getStringExtra(EditBookActivity.ID);
            String authorName = data.getStringExtra(EditBookActivity.UPDATED_AUTHOR);
            String bookName = data.getStringExtra(EditBookActivity.UPDATED_BOOK);
            String description = data.getStringExtra(EditBookActivity.UPDATED_DESCRIPTION);

            Book book = new Book(id, authorName, bookName, description);
            mSearchViewModel.update(book);

            Toast.makeText(this, R.string.updated, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, R.string.not_saved, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClickListener(Book myBook) {
        mSearchViewModel.delete(myBook);
    }
}