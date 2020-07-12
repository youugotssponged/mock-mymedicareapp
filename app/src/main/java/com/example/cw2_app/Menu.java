package com.example.cw2_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        // Access buttons
        Button menu_HomeBtn = findViewById(R.id.menu_home_btn);
        Button menu_HealthCheckBtn = findViewById(R.id.menu_healthCheck_btn);
        Button menu_ProfileBtn = findViewById(R.id.menu_Profile_btn);
        Button menu_SettingsBtn = findViewById(R.id.menu_Settings_btn);
        Button menu_LogOffBtn = findViewById(R.id.menu_logoff_btn);
        ImageView menuBtn = findViewById(R.id.menuBtn);

        // Used to navigate to the menu activity
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Menu.class));
            }
        });

        // Used to navigate to the home activity
        menu_HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Home.class));
            }
        });

        // Used to navigate to the health check activity
        menu_HealthCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, HealthCheck.class));
            }
        });

        // Used to navigate to the profile activity
        menu_ProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Profile.class));
            }
        });

        // Used to navigate to the settings activity
        menu_SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Settings.class));
            }
        });

        // Used to navigate to the main menu activity
        menu_LogOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // RETURNS TO THE MAIN SCREEN WITH A SET FLAG TO CLEAR THE ACTIVITY STACK,
                // STOPPING THE USER FROM TAPPING BACK TO GO BACK TO THE PREVIOUS ACTIVITY AFTER LOGGING OFF
                startActivity(new Intent(Menu.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                Toast.makeText(getBaseContext(), "LOGGED OFF SUCCESSFULLY!", Toast.LENGTH_SHORT).show(); // Prompt user
            }
        });

    }
}
