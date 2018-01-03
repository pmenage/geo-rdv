package android.tuto.com.rdv;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSReceivedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getExtras();
        final String phoneNumber = extra.getString("phoneNumber");
        String message = extra.getString("message");
        Pattern pattern = Pattern.compile("(.*)Latitude:.*");
        Matcher matcher = pattern.matcher(message);
        String viewMessage = "No message was found";
        if (matcher.find()) {
            viewMessage = matcher.group(1);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Message received from " + phoneNumber)
                .setMessage("Message: " + viewMessage + ". Do you want to accept this meeting?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, "Response: Accepted", null, null);
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, "Response: Declined", null, null);
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

}
