package com.example.cw2_app;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ASK USER FOR PERMISSION AS API LEVEL 23 AND ABOVE LISTS IT AS DANGEROUS, EVEN THOUGH IT IS DECLARED IN THE MANIFEST
        // Helps to stop the error of not being able to send text messages
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);


        // Create Button Listeners
        Button registerBtn = findViewById(R.id.register_btn);
        Button loginBtn = findViewById(R.id.login_btn);

        // Used to navigate to the registration activity
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registration.class));
            }
        });

        // Used to navigate to the login activity
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

    }
}