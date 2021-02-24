package com.room.ps.bookkeeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Book> mBookList;
    private OnDeleteClickListener onDeleteClickListener;

    public BookListAdapter(Context context, OnDeleteClickListener onDeleteClickListener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setBooks(List<Book> books) {
        mBookList = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = mBookList.get(position);
        holder.setData(book.getAuthor(), book.getBook(), position);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return mBookList == null ? 0 : mBookList.size();
    }

    interface  OnDeleteClickListener{
        void onDeleteClickListener(Book myBook);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private int pos = 0;
        TextView tvAuthor = itemView.findViewById(R.id.tvAuthor);
        TextView tvBook = itemView.findViewById(R.id.tvBook);
        ImageView ivRowDelete = itemView.findViewById(R.id.image_action_delete);
        CardView cardView = itemView.findViewById(R.id.cardView);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(String author, String book, int position) {
            tvAuthor.setText(author);
            tvBook.setText(book);
            this.pos = position;
        }

        public void setListeners() {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditBookActivity.class);
                    intent.putExtra("id", mBookList.get(pos).getId());
                    intent.putExtra("author", mBookList.get(pos).getAuthor());
                    intent.putExtra("book", mBookList.get(pos).getBook());
                    intent.putExtra("book_description", mBookList.get(pos).getDescription());
                    ((Activity) mContext).startActivityForResult(intent, MainActivity.UPDATE_BOOK_ACTIVITY_REQUEST_CODE);
                }
            });

            ivRowDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClickListener.onDeleteClickListener(mBookList.get(pos));
                }
            });
        }
    }
}
