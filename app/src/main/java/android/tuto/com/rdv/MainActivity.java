package android.tuto.com.rdv;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private Button smsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        smsButton = (Button) findViewById(R.id.invite);
        smsButton.setEnabled(false);
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            smsButton.setEnabled(true);
        }

        if (!checkPermission(Manifest.permission.RECEIVE_SMS)
                || !checkPermission(Manifest.permission.SEND_SMS)
                || !checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                || !checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                || !checkPermission(Manifest.permission.READ_PHONE_STATE)) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE
            }, SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        DatabaseHandler db = new DatabaseHandler(this);
        db.addUser(new User("Pauline", "15555215554"));
        db.addUser(new User("Khadija", "15555215556"));

    }

    public void sendSMS(View view) {
        EditText phoneNumberView = (EditText) findViewById(R.id.num);
        String phoneNumber = phoneNumberView.getText().toString();
        phoneNumberView.setText("");

        if (!isLocationEnabled()) {
            showLocationNotEnabledAlert();
        }

        Intent intent = new Intent(this, ChooseLocationActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);

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
            }
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void showLocationNotEnabledAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog.show();
    }

    public void getList(View v) {

        Intent intent = new Intent(this, MyListActivity.class);
        startActivity(intent);
    }

    public void seeMeetings(View view) {

        Intent intent = new Intent(this, MeetingsActivity.class);
        startActivity(intent);

    }

    public void seeNotifications(View view) {

        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);

    }

}
