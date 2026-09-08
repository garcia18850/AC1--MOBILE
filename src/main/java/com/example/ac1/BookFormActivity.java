package com.example.ac1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookFormActivity extends AppCompatActivity {
    private EditText titleInput, authorInput;
    private Spinner categorySpinner;
    private CheckBox readCheckBox;
    private Button saveButton;
    private DatabaseHelper dbHelper;
    private int bookId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_form);

        titleInput = findViewById(R.id.titleInput);
        authorInput = findViewById(R.id.authorInput);
        categorySpinner = findViewById(R.id.categorySpinner);
        readCheckBox = findViewById(R.id.readCheckBox);
        saveButton = findViewById(R.id.saveButton);
        dbHelper = new DatabaseHelper(this);

        // Preencher o Spinner de Categorias
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.book_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Verificar se é uma edição
        bookId = getIntent().getIntExtra("BOOK_ID", -1);
        if (bookId != -1) {
            Book book = dbHelper.getBookById(bookId);
            if (book != null) {
                titleInput.setText(book.getTitle());
                authorInput.setText(book.getAuthor());
                readCheckBox.setChecked(book.isRead());
                // Setar categoria no spinner
                int spinnerPosition = adapter.getPosition(book.getCategory());
                categorySpinner.setSelection(spinnerPosition);
            }
        }

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String author = authorInput.getText().toString().trim();
            String category = categorySpinner.getSelectedItem().toString();
            boolean isRead = readCheckBox.isChecked();

            // Validação de campos obrigatórios
            if (title.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "Título e Autor são obrigatórios!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (bookId == -1) {
                // Inserir novo
                dbHelper.insertBook(new Book(title, author, category, isRead));
                Toast.makeText(this, "Livro cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                // Atualizar existente
                dbHelper.updateBook(new Book(bookId, title, author, category, isRead));
                Toast.makeText(this, "Livro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}
