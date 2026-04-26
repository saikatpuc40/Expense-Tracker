package com.example.expensetracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class AddTransactionActivity extends AppCompatActivity {

    EditText amount, note, date;
    Spinner type, category;
    Button saveBtn;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        amount = findViewById(R.id.amount);
        note = findViewById(R.id.note);
        date = findViewById(R.id.date);
        type = findViewById(R.id.type);
        category = findViewById(R.id.category);
        saveBtn = findViewById(R.id.saveBtn);

        dbHelper = new DatabaseHelper(this);

        // ✅ FIX: Spinner setup MUST be inside onCreate
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this, R.array.type_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);

        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(
                this, R.array.category_array, android.R.layout.simple_spinner_item);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(catAdapter);

        saveBtn.setOnClickListener(v -> saveData());
    }

    private void saveData() {

        String amtText = amount.getText().toString().trim();

        if (amtText.isEmpty()) {
            Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_TYPE, type.getSelectedItem().toString());
        values.put(DatabaseHelper.COL_AMOUNT, Double.parseDouble(amtText));
        values.put(DatabaseHelper.COL_CATEGORY, category.getSelectedItem().toString());
        values.put(DatabaseHelper.COL_NOTE, note.getText().toString());
        values.put(DatabaseHelper.COL_DATE, date.getText().toString());

        db.insert(DatabaseHelper.TABLE_NAME, null, values);

        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();

        db.close();
        finish();
    }
}