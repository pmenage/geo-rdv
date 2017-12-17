package android.tuto.com.rdv;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

public class ServiceGPS extends Service {

    private static Thread clock;
    private static Double Latitude;
    private static Double Longitude;
    private LocationManager locationManager;
    private LocationListener onLocationChange = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

    };

    public ServiceGPS() {
    }

    public void onCreate() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!isLocationEnabled()) {
            showLocationNotEnabledAlert();
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, onLocationChange);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, onLocationChange);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        super.onCreate();
    }

    private boolean isLocationEnabled() {
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

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (clock == null) {
            clock = new Thread(new Clock());
            clock.start();
        }
        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(onLocationChange);
    }

    public static Double getLatitude() {
        if (Latitude != null) {
            return Latitude;
        } else {
            return 0.0;
        }
    }

    public static Double getLongitude() {
        if (Longitude != null) {
            return Longitude;
        } else {
            return 0.0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class Clock implements Runnable {
        public void run() {
            while (!clock.isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
