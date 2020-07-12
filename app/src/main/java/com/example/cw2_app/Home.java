package com.example.cw2_app;

// Imports
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Used to access menu button and begin check button
        ImageView menuBtn = findViewById(R.id.menuBtn);
        Button beginCheckBtn = findViewById(R.id.beginCheck_btn);

        // DISPLAY LAST READING TO THE USER
        TextView lastTemp = findViewById(R.id.lastTemp);
        TextView lastBloodLow = findViewById(R.id.lastBloodLower);
        TextView lastBloodHigh = findViewById(R.id.lastBloodHigher);
        TextView actualName = findViewById(R.id.actualName);

        // Set Default welcome text
        actualName.setText("Hi! Welcome");

        // Open Database
        MyDBHelper db = new MyDBHelper(getBaseContext());
        db.open();
            // Access for user's given name and update the welcome text
            Cursor c = db.getUser(MyDBHelper.loggedInUserID);
            actualName.setText("Hi " + c.getString(7));
        // Close Database
        db.close();

        // Used to navigate to the Menu activity
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Menu.class));
            }
        });

        // Used to navigate to the Health Check Activity
        beginCheckBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, HealthCheck.class));
            }
        });

    }
}
