package android.tuto.com.rdv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;

public class SendSMSActivity extends AppCompatActivity {

    private String phoneNumber;
    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        // TODO : get name from database

        Bundle extra = getIntent().getExtras();
        phoneNumber = extra.getString("phoneNumber");
        latitude = extra.getDouble("latitude");
        longitude = extra.getDouble("longitude");

        EditText messageView = (EditText) findViewById(R.id.message);
        messageView.setText("Bonjour, je vous propose un rendez-vous à "
                + latitude + " et " + longitude + ", Pauline");
    }

    public void sendMessage(View view) {

        EditText messageView = (EditText) findViewById(R.id.message);
        String message = messageView.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        Toast.makeText(this, "Message successfully sent", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
