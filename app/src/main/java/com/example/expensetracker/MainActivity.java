package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    TextView totalIncome, totalExpense, balance;
    Button addBtn, searchBtn;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalIncome = findViewById(R.id.income);
        totalExpense = findViewById(R.id.expense);
        balance = findViewById(R.id.balance);
        addBtn = findViewById(R.id.addBtn);
        searchBtn = findViewById(R.id.searchBtn);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        dbHelper = new DatabaseHelper(this);

        loadSummary();

        addBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AddTransactionActivity.class)));

        searchBtn.setOnClickListener(v ->
                startActivity(new Intent(this, SearchActivity.class)));

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                return true;
            }
            else if (id == R.id.nav_calendar) {
                startActivity(new Intent(this, CalendarActivity.class));
                return true;
            }
            else if (id == R.id.nav_add) {
                startActivity(new Intent(this, AddTransactionActivity.class));
                return true;
            }
            else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSummary();
    }

    private void loadSummary() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor incomeCursor = db.rawQuery(
                "SELECT SUM(amount) FROM transactions WHERE type='Income'", null);

        Cursor expenseCursor = db.rawQuery(
                "SELECT SUM(amount) FROM transactions WHERE type='Expense'", null);

        double income = 0, expense = 0;

        if (incomeCursor.moveToFirst() && incomeCursor.getString(0) != null)
            income = incomeCursor.getDouble(0);

        if (expenseCursor.moveToFirst() && expenseCursor.getString(0) != null)
            expense = expenseCursor.getDouble(0);

        totalIncome.setText("Income: " + income);
        totalExpense.setText("Expense: " + expense);
        balance.setText("Balance: " + (income - expense));

        incomeCursor.close();
        expenseCursor.close();
        db.close();
    }
}