package com.example.ac1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<Book> bookList;
    private DatabaseHelper dbHelper;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        fab = findViewById(R.id.fab);
        dbHelper = new DatabaseHelper(this);
        
        bookList = dbHelper.getAllBooks();
        updateListView();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Book selectedBook = bookList.get(position);
            Intent intent = new Intent(MainActivity.this, BookFormActivity.class);
            intent.putExtra("BOOK_ID", selectedBook.getId());
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Book selectedBook = bookList.get(position);
            showDeleteDialog(selectedBook);
            return true;
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BookFormActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookList = dbHelper.getAllBooks();
        updateListView();
    }

    private void updateListView() {
        ArrayList<String> bookTitles = new ArrayList<>();
        for (Book book : bookList) {
            String status = book.isRead() ? "[Lido]" : "[Não lido]";
            bookTitles.add(status + " " + book.getTitle() + " - " + book.getAuthor());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookTitles);
        listView.setAdapter(adapter);
    }

    private void showDeleteDialog(Book book) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir Livro")
                .setMessage("Deseja realmente excluir este livro?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    dbHelper.deleteBook(book.getId());
                    bookList = dbHelper.getAllBooks();
                    updateListView();
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
