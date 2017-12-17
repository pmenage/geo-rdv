package android.tuto.com.rdv;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class SendSMS extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private Button smsButton;
    private FusedLocationProviderClient fusedLocationClient;
    private static Double Latitude = 0.0, Longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        smsButton = (Button) findViewById(R.id.invite);
        smsButton.setEnabled(false);
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            smsButton.setEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }

        if (!checkPermission(Manifest.permission.RECEIVE_SMS)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 222);
        }

        /*
        if (!checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 333);
        }
        */

        if (!isLocationEnabled()) {
            showLocationNotEnabledAlert();
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    public void sendSMS(View view) {
        EditText phoneNumberView = (EditText) findViewById(R.id.num);
        String phoneNumber = phoneNumberView.getText().toString();

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Latitude = location.getLatitude();
                            Longitude = location.getLongitude();
                        }
                    }
                });

        if (checkPermission(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, "Latitude: " + Latitude + "; Longitude: " + Longitude, null, null);
            phoneNumberView.setText("");
            Toast.makeText(SendSMS.this, "SMS sent to " + phoneNumber, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(SendSMS.this, "Permission denied", Toast.LENGTH_SHORT).show();
        }

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

}
