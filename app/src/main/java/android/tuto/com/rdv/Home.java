package android.tuto.com.rdv;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Home extends Activity {
    private  static final int SMS_PERMISSION_CODE=1;
    // button sendSMS
    Button invite;
    // button editText
    EditText num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        invite = (Button) findViewById(R.id.invite);
        num = (EditText) findViewById(R.id.num);

        invite.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                String myMessage="hello this is my first msg";
                String number=num.getText().toString();
                sendMsg(number,myMessage);
            }

        });

    }

    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }


    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},SMS_PERMISSION_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SMS_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // SMS related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    protected void sendMsg(String number, String message) {
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(number,null,message,null,null);

    }
 //----------------------------------liste des contacts----------------------------------------
    public void getList(View v){

        Intent intent = new Intent(this,MyListActivity.class);
        startActivity(intent);
    }


}
