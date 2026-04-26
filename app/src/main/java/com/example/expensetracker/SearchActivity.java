package com.example.expensetracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    EditText searchText;
    ListView listView;
    DatabaseHelper dbHelper;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText = findViewById(R.id.searchText);
        listView = findViewById(R.id.listView);
        Button searchBtn = findViewById(R.id.searchBtn);

        dbHelper = new DatabaseHelper(this);

        searchBtn.setOnClickListener(v -> search());
    }

    private void search() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String keyword = searchText.getText().toString().trim();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM transactions WHERE category LIKE ? OR note LIKE ?",
                new String[]{"%" + keyword + "%", "%" + keyword + "%"});

        String[] data = new String[cursor.getCount()];
        int i = 0;

        while (cursor.moveToNext()) {
            data[i++] = cursor.getString(1) + " | " +
                    cursor.getDouble(2) + " | " +
                    cursor.getString(3);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        cursor.close();
        db.close();
    }
}