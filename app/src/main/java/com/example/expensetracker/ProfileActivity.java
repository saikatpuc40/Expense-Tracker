package com.example.expensetracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPhone;
    private Button btnEditProfile, btnLogout;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);

        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // SharedPreferences (temporary user data storage)
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        loadUserData();

        // Edit Profile button
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this,
                        "Edit Profile Clicked",
                        Toast.LENGTH_SHORT).show();

                // TODO: Open EditProfileActivity
                // Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                // startActivity(intent);
            }
        });

        // Logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Toast.makeText(ProfileActivity.this,
                        "Logged out successfully",
                        Toast.LENGTH_SHORT).show();

                // Redirect to login page
                finish();
            }
        });
    }

    private void loadUserData() {
        String name = sharedPreferences.getString("name", "No Name");
        String email = sharedPreferences.getString("email", "No Email");
        String phone = sharedPreferences.getString("phone", "No Phone");

        tvName.setText(name);
        tvEmail.setText(email);
        tvPhone.setText(phone);
    }
}
