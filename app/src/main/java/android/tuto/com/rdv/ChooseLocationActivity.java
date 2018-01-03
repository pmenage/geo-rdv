package android.tuto.com.rdv;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class ChooseLocationActivity extends AppCompatActivity {

    private String phoneNumber;
    private FusedLocationProviderClient fusedLocationClient;
    private static Double Latitude = 0.0, Longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        Bundle extra = getIntent().getExtras();
        phoneNumber = extra.getString("phoneNumber");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    public void sendUserLocation(View view) {

        try {
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
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        startIntent();

    }

    public void sendOtherLocation(View view) {

        EditText latitudeView = (EditText) findViewById(R.id.latitude);
        Latitude = Double.parseDouble(latitudeView.getText().toString());

        EditText longitudeView = (EditText) findViewById(R.id.longitude);
        Longitude = Double.parseDouble(longitudeView.getText().toString());

        startIntent();

    }

    public void startIntent() {
        Intent intent = new Intent(this, SendSMSActivity.class);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("latitude", Latitude);
        intent.putExtra("longitude", Longitude);
        startActivity(intent);
    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
