package com.room.ps.bookkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditBookActivity extends AppCompatActivity {

    public static final String ID = "book_id";
    public static final String UPDATED_AUTHOR = "author_name";
    public static final String UPDATED_BOOK = "book_name";

    public String id;
    private TextView mEtAuthor;
    private TextView mEtBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Button bSave = findViewById(R.id.bAdd);
        Button bCancel = findViewById(R.id.bCancel);

        mEtAuthor = findViewById(R.id.etAuthorName);
        mEtBook = findViewById(R.id.etBookName);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            id = bundle.getString("id");
            String book = bundle.getString("book");
            String author = bundle.getString("author");

            mEtAuthor.setText(author);
            mEtBook.setText(book);
        }

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updated_author = mEtAuthor.getText().toString();
                String updated_book = mEtBook.getText().toString();

                Intent resultIntent = new Intent();
                resultIntent.putExtra(ID, id);
                resultIntent.putExtra(UPDATED_AUTHOR, updated_author);
                resultIntent.putExtra(UPDATED_BOOK, updated_book);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}