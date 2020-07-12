package com.example.cw2_app;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Access Clickable buttons and link
        ImageView backBtn = findViewById(R.id.backBtn);
        Button mainRegisterBtn = findViewById(R.id.mainRegister_btn);
        TextView loginLink = findViewById(R.id.loginlink);

        // Access entered fields by the user
        // USED FOR VERIFICATION
        final EditText username = findViewById(R.id.usernameEntry);
        final EditText email = findViewById(R.id.emailEntry);

        // Access entered fields by the user
        // USED FOR ADDING TO THE DATABASE
        final EditText password = findViewById(R.id.passwordEntry);
        final EditText phoneNumber = findViewById(R.id.phoneNumberEntry);
        final EditText gpName = findViewById(R.id.gpNameEntry);
        final EditText gpNumber = findViewById(R.id.gpNumberEntry);

        // Used to navigate to the main screen
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, MainActivity.class));
            }
        });

        // Used to varify user and or navigate to the login screen if successful
        mainRegisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // PARSE ENTRY TEXT TO READIBLE STRINGS
                String enteredUsername = username.getText().toString();
                String enteredEmail = email.getText().toString();
                String enteredPassword = password.getText().toString();
                String enteredPhoneNumber = phoneNumber.getText().toString();
                String enteredGPName = gpName.getText().toString();
                String enteredGPNumber = gpNumber.getText().toString();
                String enteredActualName = ""; // DEFAULT

                // Flag to determine if the data should be added to the database
                boolean shouldInsert;

                // Open Database
                MyDBHelper db = new MyDBHelper(getBaseContext());
                // Check DB if a record matches
                db.open();
                try {
                    // Get all users from database
                    Cursor c = db.getAllUsers();
                    // Move to first record found
                    if(c.moveToFirst()){
                        do {
                            // Check if the username and the email record match both username and password
                            if(enteredUsername.equals(c.getString(1)) || enteredEmail.equals(c.getString(3))){
                                Toast.makeText(getBaseContext(), "Username / Email already exists!", Toast.LENGTH_LONG).show(); // PROMPT USER
                                Log.w("DATABASE CHECK: ", "USER ALREADY EXISTS"); // DEBUG
                                shouldInsert = false; // set flag
                                db.close(); // Close database
                                break; // Break from loop
                            } else{
                                shouldInsert = true; // Set flag
                                Log.w("DATABASE CHECK: ", "INSERTION NOW TOGGLED"); // DEBUG
                            }
                        } while(c.moveToNext());  // Move to next record and check
                        // if should Insert User
                        if(shouldInsert){
                            // Run insertion query
                            db.insertUser(enteredUsername, enteredPassword, enteredEmail, enteredPhoneNumber, enteredGPName, enteredGPNumber, enteredActualName);
                            // Prompt user
                            Toast.makeText(getBaseContext(), "REGISTRATION SUCCESSFUL", Toast.LENGTH_SHORT).show();
                            // Navigate to the login screen
                            startActivity(new Intent(Registration.this, Login.class));
                        }

                    }
                }
                // Catch any exceptions
                catch(Exception e){
                    Log.e("NO DATA - DATABASE:", "PLEASE CHECK DATABASE TABLE"); //DEBUG
                    db.close(); // close database
                }
                db.close(); // close database
                Log.d("Register Button: ", "REGISTER CLICKED"); // Debug
            }
        });

        // Used to navigate to the login page
        loginLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Login.class));
            }
        });
    }

}
