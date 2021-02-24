package com.room.ps.bookkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.room.ps.bookkeeper.MainActivity.NEW_AUTHOR;
import static com.room.ps.bookkeeper.MainActivity.NEW_BOOK;

public class NewBookActivity extends AppCompatActivity {

    private TextView mEtAuthor;
    private TextView mEtBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Button bAdd = findViewById(R.id.bAdd);
        Button bCancel = findViewById(R.id.bCancel);

        mEtAuthor = findViewById(R.id.etAuthorName);
        mEtBook = findViewById(R.id.etBookName);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = getIntent();
                if(TextUtils.isEmpty(mEtAuthor.getText()) ||
                        (TextUtils.isEmpty(mEtBook.getText()))){
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                } else {
                    String author = mEtAuthor.getText().toString();
                    String book = mEtBook.getText().toString();

                    resultIntent.putExtra(NEW_AUTHOR, author);
                    resultIntent.putExtra(NEW_BOOK, book);
                    setResult(Activity.RESULT_OK, resultIntent);
                }
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