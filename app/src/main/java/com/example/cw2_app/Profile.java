package com.example.cw2_app;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Access buttons
        ImageView menuBtn = findViewById(R.id.menuBtn);
        Button saveBtn = findViewById(R.id.save_btn);
        Button chooseContactBtn = findViewById(R.id.changeContact_btn);

        // Access entered values
        final EditText nameEdit = findViewById(R.id.changeNameEntry);
        final EditText numberEdit = findViewById(R.id.changeNumberEntry);

        // Open the database
        final MyDBHelper db = new MyDBHelper(getBaseContext());
        db.open();
        // Get user record
        Cursor c = db.getUser(MyDBHelper.loggedInUserID);
        // SHOW DATA IN EDIT TEXTS
        nameEdit.setText(c.getString(7));
        numberEdit.setText(c.getString(4));
        // Close database
        db.close();

        // Used to navigate to the menu activity
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Menu.class));
            }
        });

        // Used to choose a contact
        chooseContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(ContactsContract.Contacts.CONTENT_TYPE);
                startActivity(i);
            }
        });

        // Used to update a given user record's information
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open database
                db.open();
                // UPDATE
                // Access entered information
                String enteredNameEdit = nameEdit.getText().toString();
                String enteredNumberEdit = numberEdit.getText().toString();

                // Run update querys
                db.updateUserActualName(enteredNameEdit);
                db.updateUserNumber(enteredNumberEdit);

                // Access user
                Cursor c = db.getUser(MyDBHelper.loggedInUserID);
                // Update text field
                numberEdit.setText(c.getString(4));

                // Close database
                db.close();
            }
        });
    }
}
