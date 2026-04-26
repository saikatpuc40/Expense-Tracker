package com.example.expensetracker;

public class TransactionModel {

    String type, category, note, date;
    double amount;

    public TransactionModel(String type, double amount, String category, String note, String date) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getNote() { return note; }
    public String getDate() { return date; }
}