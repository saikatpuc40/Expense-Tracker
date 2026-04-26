package com.example.expensetracker;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView selectedDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Initialize views
        calendarView = findViewById(R.id.calendarView);
        selectedDateText = findViewById(R.id.selectedDateText);

        // Default selected date (today)
        long today = calendarView.getDate();
        updateDateText(today);

        // Date change listener
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                // Month is 0-based
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;

                selectedDateText.setText("Selected Date: " + date);

                // TODO: Load expenses for this date from database
                loadExpensesByDate(date);

                Toast.makeText(CalendarActivity.this,
                        "Selected: " + date,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDateText(long dateInMillis) {
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("dd/MM/yyyy");

        String date = sdf.format(new java.util.Date(dateInMillis));
        selectedDateText.setText("Selected Date: " + date);
    }

    // 🔥 This is where you connect your database later
    private void loadExpensesByDate(String date) {
        // Example:
        // DBHelper db = new DBHelper(this);
        // List<Expense> list = db.getExpensesByDate(date);
        // update RecyclerView here

        System.out.println("Load expenses for: " + date);
    }
}