package com.example.cw2_app;

// Imports
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


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Access Clickable buttons and link
        ImageView backBtn = findViewById(R.id.backBtn);
        Button mainLoginBtn = findViewById(R.id.mainLogin_btn);
        TextView registerLink = findViewById(R.id.registerlink);

        // Access entered fields by the user
        // USED FOR VALIDATION
        final EditText username = findViewById(R.id.usernameEntry);
        final EditText password = findViewById(R.id.passwordEntry);

        // Used to navigate to the main screen
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });

        // Used to verify user and / or navigate to the home screen if successful
        mainLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Convert entered information into program readible strings
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();

                // Flag for determining if the user was successful in logging in
                boolean loginSuccess = false;

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
                            // Check if the username and the password record match both username and password
                            if(enteredUsername.equals(c.getString(1)) && enteredPassword.equals(c.getString(2))){
                                Toast.makeText(getBaseContext(), "Login Successful!", Toast.LENGTH_LONG).show(); // Prompt User of success
                                Log.w("LOGIN STATUS: ", "SUCCESSFUL"); // DEBUG
                                loginSuccess = true; // Set flag

                                // SET LOGGED IN ID
                                MyDBHelper.loggedInUserID = Long.parseLong(c.getString(0)); // Assign ID to reference later
                                // Close Database
                                db.close();
                                // Navigate to the Home Screen
                                startActivity(new Intent(Login.this, Home.class));
                            }
                            // If they don't match, set flag to false
                            else{
                                loginSuccess = false;
                            }

                        } while(c.moveToNext()); // Move to next record and check

                        // If Unsuccessful
                        if(!loginSuccess)
                            Toast.makeText(getBaseContext(), "Incorrect USERNAME / PASSWORD!", Toast.LENGTH_LONG).show(); // Prompt user

                    }
                }
                // Catch exception
                catch(Exception e){
                    Log.e("NO DATA - DATABASE:", "PLEASE CHECK DATABASE TABLE"); // DEBUG
                    db.close(); // close database
                }

                db.close(); // Close database
                Log.d("LOGIN BUTTON: ", "LOGIN CLICKED"); // DEBUG
            }
        });

        // Used to navigate to the registration activity screen
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registration.class));
            }
        });

    }


}
