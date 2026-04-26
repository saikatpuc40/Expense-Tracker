package com.example.expensetracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView totalIncome, totalExpense, balance;
    Button addBtn;

    RecyclerView recyclerView;
    TransactionAdapter adapter;
    ArrayList<TransactionModel> list;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalIncome = findViewById(R.id.income);
        totalExpense = findViewById(R.id.expense);
        balance = findViewById(R.id.balance);
        addBtn = findViewById(R.id.addBtn);

        recyclerView = findViewById(R.id.recyclerView);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        dbHelper = new DatabaseHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadSummary();
        loadTransactions();

        addBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AddTransactionActivity.class)));

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_calendar) {
                startActivity(new Intent(this, CalendarActivity.class));
                return true;
            } else if (id == R.id.nav_add) {
                startActivity(new Intent(this, AddTransactionActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
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
        loadTransactions();
    }

    // ================= SUMMARY =================
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

        totalIncome.setText("Income\n৳ " + income);
        totalExpense.setText("Expense\n৳ " + expense);
        balance.setText("Balance\n৳ " + (income - expense));

        incomeCursor.close();
        expenseCursor.close();
        db.close();
    }

    // ================= TRANSACTION LIST =================
    private void loadTransactions() {

        list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM transactions ORDER BY id DESC", null);

        while (cursor.moveToNext()) {

            list.add(new TransactionModel(
                    cursor.getString(1),   // type
                    cursor.getDouble(2),   // amount
                    cursor.getString(3),   // category
                    cursor.getString(4),   // note
                    cursor.getString(5)    // date
            ));
        }

        adapter = new TransactionAdapter(this, list);
        recyclerView.setAdapter(adapter);

        cursor.close();
        db.close();
    }
}