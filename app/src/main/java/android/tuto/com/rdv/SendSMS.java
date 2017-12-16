package android.tuto.com.rdv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendSMS extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private Button smsButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        smsButton = (Button) findViewById(R.id.invite);
        smsButton.setEnabled(false);
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            smsButton.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        if (!checkPermission(Manifest.permission.RECEIVE_SMS)) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECEIVE_SMS}, 222);
        }

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText phoneNumberView = (EditText) findViewById(R.id.num);
                String phoneNumber = phoneNumberView.getText().toString();

                if (checkPermission(Manifest.permission.SEND_SMS)) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, "Hello", null, null);
                    phoneNumberView.setText("");
                    Toast.makeText(SendSMS.this, "SMS sent to " + phoneNumber, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SendSMS.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return (checkPermission == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    smsButton.setEnabled(true);
                }
                return;
            }
        }
    }

    public void getList(View v){

        Intent intent = new Intent(this,MyListActivity.class);
        startActivity(intent);
    }

}
