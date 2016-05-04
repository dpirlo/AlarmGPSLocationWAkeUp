package com.example.geniod.mapsgoogleapp;
import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;


public class ServicioDespetador extends Service {
    public LocationManager locationManager;
    long POLLING_FREQ = 1000 * 3;
    long FASTEST_UPDATE_FREQ = 1000 * 5;
    double longitud;
    double latitud;
    double longitudMaps;
    double latitudMaps;
    double distancia=0;
    int radio;

    LocationRequest mLocationRequest;
    Location location;
    android.location.LocationListener locationlistener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public ServicioDespetador() {

    }

    @Override
    public IBinder onBind(Intent intent) {/*
        Bundle bundle = intent.getExtras();
        latitud = (bundle.getDouble("latitud"));
        longitud = (bundle.getDouble("longitud"));
        longitudMaps = (bundle.getDouble("longitudMaps"));
        latitudMaps = (bundle.getDouble("latitudMaps"));
        final int radio = 200;


        double latitudmat;
        double longitudmat;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 200, locationlistener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 200, locationlistener);

        {
            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled) {
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                if (location != null) {

                    //location = lfyl.getLocation();
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                    // latitud a metros
                    latitudmat = Math.toDegrees(latitud) * 1851;

                    longitudmat = Math.toDegrees(longitud) * 1851;

                    distancia = (Math.sqrt((latitudmat - latitudMaps) * (latitudmat - latitudMaps) + (longitudmat - longitudMaps) * (longitudmat - longitudMaps)));
                    //textView.setText(String.valueOf(distancia));
                }

            } else if (isNetworkEnabled) {

                if (locationManager != null) {

                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                if (location != null) {

                    //location = lfyl.getLocation();
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();

                    latitudmat = Math.toDegrees(latitud) * 1851; // latitud a metros
                    longitudmat = Math.toDegrees(longitud) * 1851;

                    distancia = (Math.sqrt((latitudmat - latitudMaps) * (latitudmat - latitudMaps) + (longitudmat - longitudMaps) * (longitudmat - longitudMaps)));
                    textView.setText(String.valueOf(distancia));
                }


            } else
                Toast.makeText(ServicioDespetador.this, "No hay red habilitada", Toast.LENGTH_SHORT).show();

        }

*/
        return null;
    }

    @Override
    public void onCreate() {
        //Context context = getApplicationContext();
        /*
        locationlistener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(POLLING_FREQ);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);
*/
        //setContentView(R.layout.activity_config);
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        final Context context = getApplicationContext();
        Bundle bundle = intent.getExtras();
        latitud = (bundle.getDouble("latitud"));
        longitud = (bundle.getDouble("longitud"));
        longitudMaps = (bundle.getDouble("longitudMaps"));
        latitudMaps = (bundle.getDouble("latitudMaps"));
        radio = (bundle.getInt("radio"));
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(POLLING_FREQ);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 200, locationlistener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 200, locationlistener);

       new Thread(new Runnable() {

            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ignored) {
                    }

                     {

                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(ServicioDespetador.this, /*(int) distancia*/ "no funca red", Toast.LENGTH_SHORT).show();
                        }

                        //EditText etradio;
                        // int radio = 200;

                          // getting GPS status
                            boolean isGPSEnabled = locationManager
                                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

                            // getting network status
                            boolean isNetworkEnabled = locationManager
                                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                            if (isGPSEnabled) {
                                if (locationManager != null) {
                                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                                if (location != null) {

                                    latitud = location.getLatitude();
                                    longitud = location.getLongitude();
                                    // latitud a metros


                                    distancia = (1851*Math.sqrt((latitud - latitudMaps) * (latitud - latitudMaps) + (longitud - longitudMaps) * (longitud - longitudMaps)));
                                    //textView.setText(String.valueOf(distancia));
                                }
                                }

                            } else if (isNetworkEnabled) {

                                if (locationManager != null) {

                                  //  location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                                if (location != null) {

                                    latitud = location.getLatitude();
                                    longitud = location.getLongitude();



                                    distancia = (1851*Math.sqrt((latitud - latitudMaps) * (latitud - latitudMaps) + (longitud - longitudMaps) * (longitud - longitudMaps)));

                                }
                                }
                            }
                    }
                } while (distancia>radio);
                Toast.makeText(ServicioDespetador.this, /*(int) distancia*/ "llegamos", Toast.LENGTH_SHORT).show();
                stopSelf();
            }

        }).start();


        return 0;
    }
}
