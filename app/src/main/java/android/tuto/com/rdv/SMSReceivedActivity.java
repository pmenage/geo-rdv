package android.tuto.com.rdv;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;

public class SMSReceivedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getExtras();
        final String phoneNumber = extra.getString("phoneNumber");
        String message = extra.getString("message");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Message received from " + phoneNumber)
                .setMessage("Message: " + message + ". Do you want to accept this meeting?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Send Text message to sender
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, "Response: Accepted", null, null);
                        // Put boolean in database to say that it is a response to a message
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Send Text message to sender
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, "Response: Declined", null, null);
                        // Put boolean in database to say that it is a response
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
