package com.room.ps.bookkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.room.ps.bookkeeper.MainActivity.NEW_AUTHOR;
import static com.room.ps.bookkeeper.MainActivity.NEW_BOOK;
import static com.room.ps.bookkeeper.MainActivity.NEW_DESCRIPTION;

public class NewBookActivity extends AppCompatActivity {

    private TextView mEtAuthor;
    private TextView mEtBook;
    private EditText mEtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Button bAdd = findViewById(R.id.bAdd);
        Button bCancel = findViewById(R.id.bCancel);
        TextView tvLastUpdated = findViewById(R.id.tvxLastUpdated);

        tvLastUpdated.setVisibility(View.INVISIBLE);

        mEtAuthor = findViewById(R.id.etAuthorName);
        mEtBook = findViewById(R.id.etBookName);
        mEtDescription = findViewById(R.id.etDescription);

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
                    String description = mEtDescription.getText().toString();

                    resultIntent.putExtra(NEW_AUTHOR, author);
                    resultIntent.putExtra(NEW_BOOK, book);
                    resultIntent.putExtra(NEW_DESCRIPTION, description);
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