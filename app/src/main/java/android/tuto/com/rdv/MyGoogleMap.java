package android.tuto.com.rdv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyGoogleMap extends AppCompatActivity implements OnMapReadyCallback {

    private static Double Latitude;
    private static Double Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_google_map);
        Bundle extra = getIntent().getExtras();
        Latitude = extra.getDouble("latitude");
        Longitude = extra.getDouble("longitude");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng position = new LatLng(Latitude, Longitude);
        googleMap.addMarker(new MarkerOptions().position(position)
                .title("This is your meeting"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
}
