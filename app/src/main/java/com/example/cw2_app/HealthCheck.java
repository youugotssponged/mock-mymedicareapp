
package com.example.cw2_app;

// Imports
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HealthCheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_check);

        // Used to Access the Menu button and start Buttons
        ImageView menuBtn = findViewById(R.id.menuBtn);
        Button startCheckBtn = findViewById(R.id.startCheck_btn);

        // Used for pushing a notification to the device
        final NotificationManager notifier = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Used to receive the values inputted by the user
        final EditText tempEdit = findViewById(R.id.temperatureEntry);
        final EditText bloodLowEdit = findViewById(R.id.bloodLowEntry);
        final EditText bloodHighEdit = findViewById(R.id.bloodHighEntry);
        final EditText heartRateEdit = findViewById(R.id.heartRateEntry);

        // Menu Button Click Listener
        // Used to navigate to the Menu Activity
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Starts the Menu Activity
                startActivity(new Intent(HealthCheck.this, Menu.class));
            }
        });

        // Start Check Button Listener
        // Used to preform the health check as outlined within the portfolio guidelines
        startCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Convert entry into program readible strings
                String s_tempEdit = tempEdit.getText().toString();
                String s_bloodLowEdit = bloodLowEdit.getText().toString();
                String s_bloodHighEdit = bloodHighEdit.getText().toString();
                String s_heartRateEdit = heartRateEdit.getText().toString();

                // Flags used to determine if the user is at high or low risk
                boolean isHighRisk = false;
                boolean isLowRisk = false;

                // Check if the user has entered all information into each field
                // If any of them are empty, prompt the user with a message via Toast
                if(s_tempEdit.isEmpty() || s_bloodLowEdit.isEmpty() || s_bloodHighEdit.isEmpty() || s_heartRateEdit.isEmpty()){
                    Toast.makeText(getBaseContext(), "Please enter all readings!", Toast.LENGTH_SHORT).show();
                }
                // Else begin check
                else{

                    // Access result output components
                    TextView temperatureResult = findViewById(R.id.temperatureResult);
                    TextView bloodLowResult = findViewById(R.id.bloodLowResult);
                    TextView bloodHighResult = findViewById(R.id.bloodHighResult);
                    TextView heartRateResult = findViewById(R.id.heartRateResult);

                    // Converted Inputs to Double
                    double n_tempEdit = Double.parseDouble(s_tempEdit);
                    double n_bloodLowEdit = Double.parseDouble(s_bloodLowEdit);
                    double n_bloodHighEdit = Double.parseDouble(s_bloodHighEdit);
                    double n_heartRateEdit = Double.parseDouble(s_heartRateEdit);

                    // Check temperature
                    // If temperature is normal
                    if(n_tempEdit <= 37.0){
                        temperatureResult.setText("Temp: Normal"); // Set result text
                        temperatureResult.setTextColor(Color.GREEN); // Assign Color
                    }
                    // Else If temperature is Low Risk
                    else if(n_tempEdit > 37.0 && n_tempEdit < 38.0){
                        temperatureResult.setText("Temp: Low Risk"); // Set result text
                        temperatureResult.setTextColor(Color.MAGENTA); // Assign Color
                        isLowRisk = true; // Toggle Flag
                    }
                    // Else If temperature High risk
                    else if(n_tempEdit >= 38.0){
                        temperatureResult.setText("Temp: High Risk"); // Set result text
                        temperatureResult.setTextColor(Color.RED); // Assign Color
                        isHighRisk = true; // Toggle flag
                    }


                    // Check blood low reading
                    // If blood low reading is normal
                    if(n_bloodLowEdit <= 80.0){
                        bloodLowResult.setText("Blood Low: Normal"); // Set result text
                        bloodLowResult.setTextColor(Color.GREEN); // Assign color
                    }
                    // Else if low risk
                    else if(n_bloodLowEdit > 80.0 && n_bloodLowEdit < 110.0){
                        bloodLowResult.setText("Blood Low: Low Risk"); // Set result text
                        bloodLowResult.setTextColor(Color.MAGENTA); // Assign color
                        isLowRisk = true; // Toggle flag
                    }
                    // Else if high risk
                    else if(n_bloodLowEdit > 110){
                        bloodLowResult.setText("Blood Low: High Risk"); // Set result text
                        bloodLowResult.setTextColor(Color.RED); // Assign color
                        isHighRisk = true; // Toggle flag
                    }

                    // Check blood high
                    if(n_bloodHighEdit < 120.0){
                        bloodHighResult.setText("Blood High: Normal"); // Set result text
                        bloodHighResult.setTextColor(Color.GREEN); // Assign color
                    }
                    // Else if low risk
                    else if(n_bloodHighEdit >= 120.0 && n_bloodHighEdit < 180.0){
                        bloodHighResult.setText("Blood High: Low Risk"); // Set result text
                        bloodHighResult.setTextColor(Color.MAGENTA); // Assign color
                        isLowRisk = true; // Toggle flag
                    }
                    // Else if high risk
                    else if(n_bloodHighEdit > 180.0){
                        bloodHighResult.setText("Blood High: High Risk"); // Set result text
                        bloodHighResult.setTextColor(Color.RED); // Assign color
                        isHighRisk = true; // Toggle flag
                    }


                    // Check heart rate
                    if(n_heartRateEdit == 72.0){
                        heartRateResult.setText("Heart Rate: Normal" ); // Set result text
                        heartRateResult.setTextColor(Color.GREEN); // Assign color
                    }
                    // Else if low risk
                    else if(n_heartRateEdit > 72.0 && n_heartRateEdit < 160.0){
                        heartRateResult.setText("Heart Rate: Low Risk"); // Set result text
                        heartRateResult.setTextColor(Color.MAGENTA); // Assign color
                        isLowRisk = true; // Toggle flag
                    }
                    // Else if high risk
                    else {
                        heartRateResult.setText("Heart Rate: High Risk"); // Set result text
                        heartRateResult.setTextColor(Color.RED); // Assign color
                        isHighRisk = true; // Toggle flag
                    }

                }

                // If any parameter is categorised as high risk
                if(isLowRisk) {
                    // Prompt to the user of status
                    Toast.makeText(getBaseContext(), "You are at Low risk, please take more care!", Toast.LENGTH_LONG).show();

                    // Build Notification
                    NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext());
                    notification.setSmallIcon(R.drawable.ic_launcher_foreground);
                    notification.setContentTitle("MyMedicare Health Check!");
                    notification.setContentText("You are at low risk!, Please take more care");
                    notification.setWhen(System.currentTimeMillis());

                    // Set notification activity
                    Intent notificationIntent = new Intent(HealthCheck.this, HealthCheck.class);
                    notification.setContentIntent(PendingIntent.getActivity(HealthCheck.this, 0, notificationIntent, 0));

                    // Push Notification
                    notifier.notify(0x1001, notification.build());

                    // Open Database
                    MyDBHelper db = new MyDBHelper(getBaseContext());
                    db.open();
                        // Access user's number to text them
                        Cursor c = db.getUser(MyDBHelper.loggedInUserID);
                        String usersNumber = c.getString(4);

                        // Send Text
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(usersNumber, null, "You are at low risk!, Please take more care and be aware.", null, null);
                     // Close Database
                     db.close();
                }
                else if(isHighRisk) {
                    // Prompt to the user of status
                    Toast.makeText(getBaseContext(), "You are at high risk, your doctor/carer will be notified!", Toast.LENGTH_LONG).show();

                    // Build Notification
                    NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext());
                    notification.setSmallIcon(R.drawable.ic_launcher_foreground);
                    notification.setContentTitle("MyMedicare Health Check!");
                    notification.setContentText("You are at high risk!, we have notified your doctor");
                    notification.setWhen(System.currentTimeMillis());

                    // Set notification activity
                    Intent notificationIntent = new Intent(HealthCheck.this, HealthCheck.class);
                    notification.setContentIntent(PendingIntent.getActivity(HealthCheck.this, 0, notificationIntent, 0));

                    // Push Notification
                    notifier.notify(0x1001, notification.build());

                    // Open Database
                    MyDBHelper db = new MyDBHelper(getBaseContext());
                    db.open();
                        // Access user's number and their doctor's number
                        Cursor c = db.getUser(MyDBHelper.loggedInUserID);
                        String doctorsNumber = c.getString(6);
                        String usersNumber = c.getString(4);

                        // Send both recipients a text message
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(doctorsNumber, null, c.getString(7) + " is at High Risk!", null, null);
                        sms.sendTextMessage(usersNumber, null, "You are at high risk!, we have notified your doctor.", null, null);
                    // Close database
                    db.close();
                }
            }
        });
    }
}
