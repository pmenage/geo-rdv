package android.tuto.com.rdv;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SendSMSActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /* Article Medium */
    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    /* Article Medium */
    private void requestReadAndSendSmsPermission() {
        /*if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {

        }*/
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SMS_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SendSMSActivity.this, "Permission accepted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SendSMSActivity.this, "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void sendSMS(View view) {

        TextView phoneNumberView = (TextView) findViewById(R.id.enterNum);
        String phoneNumber = phoneNumberView.toString();

//        if (Build.VERSION.SDK_INT >= 23) {
            if (isSmsPermissionGranted()) {
                sendMessage(phoneNumber);
            } else {
                requestReadAndSendSmsPermission();
            }
//        }

    }

    public void sendMessage(String phoneNumber) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, "Coucou", null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

}