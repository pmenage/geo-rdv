package android.tuto.com.rdv;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

public class SendSMSActivity extends AppCompatActivity {

    private String phoneNumber;
    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        Bundle extra = getIntent().getExtras();
        phoneNumber = extra.getString("phoneNumber");
        latitude = extra.getDouble("latitude");
        longitude = extra.getDouble("longitude");

        // TODO : check if is emulator or not
        DatabaseHandler db = new DatabaseHandler(this);
        User user = db.getUserByPhoneNumber("1555521" + phoneNumber);

        EditText messageView = (EditText) findViewById(R.id.message);
        messageView.setText("Bonjour, je vous propose un rendez-vous à "
                + latitude + " et " + longitude + ", " + user.getName());
    }

    public void sendMessage(View view) {

        EditText messageView = (EditText) findViewById(R.id.message);
        String message = messageView.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        String viewMessage = message + "Latitude:" + latitude + "Longitude:" + longitude;
        smsManager.sendTextMessage(phoneNumber, null, viewMessage, null, null);

        DatabaseHandler db = new DatabaseHandler(this);
        db.addMeeting(new Meeting(Double.toString(latitude), Double.toString(longitude), viewMessage, (new Date()).toString()));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
