package com.example.geniod.mapsgoogleapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class ConfigActivity extends AppCompatActivity {
    private static final long POLLING_FREQ = 1000 * 3;
    private static final long FASTEST_UPDATE_FREQ = 1000 * 5;
    private TextView textView;
    double longitud;
    double latitud;
    double longitudMaps;
    double latitudMaps;
    double distancia;
    public LocationRequest mLocationRequest;
    public Location location ;
    public GoogleApiClient client;
    public LocationManager locationManager;
    public EditText etradio;
    public int radio =200;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        client = new GoogleApiClient() {

            @Override
            public boolean hasConnectedApi(@NonNull Api<?> api) {
                return false;
            }

            @NonNull
            @Override
            public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
                return null;
            }

            @Override
            public void connect() {

            }

            @Override
            public ConnectionResult blockingConnect() {
                return null;
            }

            @Override
            public ConnectionResult blockingConnect(long l, @NonNull TimeUnit timeUnit) {
                return null;
            }

            @Override
            public void disconnect() {

            }

            @Override
            public void reconnect() {

            }

            @Override
            public PendingResult<Status> clearDefaultAccountAndReconnect() {
                return null;
            }

            @Override
            public void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {

            }

            @Override
            public boolean isConnected() {
                return false;
            }

            @Override
            public boolean isConnecting() {
                return false;
            }

            @Override
            public void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {

            }

            @Override
            public boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks) {
                return false;
            }

            @Override
            public void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {

            }

            @Override
            public void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {

            }

            @Override
            public boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
                return false;
            }

            @Override
            public void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {

            }

            @Override
            public void dump(String s, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strings) {

            }
        };
        super.onCreate(savedInstanceState);

        //etradio = (EditText) findViewById(R.id.etradio);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(POLLING_FREQ);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);

        setContentView(R.layout.activity_config);

        textView = (TextView) findViewById(R.id.tvdistancia);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 200, locationlistener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 200, locationlistener);
        }

        // getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        locationlistener.onLocationChanged(location);

        Bundle bundle = getIntent().getExtras();
        latitud = (bundle.getDouble("latitud"));
        longitud = (bundle.getDouble("longitud"));
        longitudMaps = (bundle.getDouble("longitudMaps"));
        latitudMaps = (bundle.getDouble("latitudMaps"));

        double latitudmat = Math.toDegrees(latitud) * 1851; // latitud a metros
        double longitudmat = Math.toDegrees(longitud) * 1851;
        double longitudMapscalc = Math.toDegrees(longitudMaps) * 1851;
        double latitudMapscalc = Math.toDegrees(latitudMaps) * 1851;
        distancia = (Math.sqrt((latitudmat - latitudMapscalc) * (latitudmat - latitudMapscalc) + (longitudmat - longitudMapscalc) * (longitudmat - longitudMapscalc)));

        textView.setText(String.valueOf(distancia));

        if (isGPSEnabled ) {
            if (locationManager != null){

            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if (location != null ) {

                //location = lfyl.getLocation();
                latitud = location.getLatitude();
                longitud = location.getLongitude();
            }

        } else if(isNetworkEnabled){

            if (locationManager != null){

            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            if (location != null ) {

                //location = lfyl.getLocation();
                latitud = location.getLatitude();
                longitud = location.getLongitude();
            }


        } else {

            Toast.makeText(ConfigActivity.this, "No hay red habilitada", Toast.LENGTH_SHORT).show();
        }
        new getlocation().execute();

       // radio=  Integer.parseInt(etradio.getText().toString());
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private class getlocation extends AsyncTask<String, String, Void> {


        protected void onProgressUpdate(String... progress) {

        }


        protected void onPostExecute(Void result) {

        }


        @Override
        protected Void doInBackground(String... params) {
/*
            /////////////////////////////////////////////////////////
            double latitudmat;
            double longitudmat;
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 200, locationlistener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 200, locationlistener);

            while (distancia>radio){
                //Toast.makeText(ConfigActivity.this, "enra", Toast.LENGTH_SHORT).show();

                try {
                    Thread.sleep(POLLING_FREQ);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled ) {
                if (locationManager != null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                if (location != null ) {

                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                     // latitud a metros
                    latitudmat = Math.toDegrees(latitud) * 1851;

                    longitudmat = Math.toDegrees(longitud) * 1851;

                    distancia =(Math.sqrt((latitudmat - latitudMaps) * (latitudmat - latitudMaps) + (longitudmat - longitudMaps) * (longitudmat - longitudMaps)));
                    //textView.setText(String.valueOf(distancia));
                }

            } else if(isNetworkEnabled){

                if (locationManager != null){

                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                if (location != null ) {

                    //location = lfyl.getLocation();
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();

                    latitudmat = Math.toDegrees(latitud) * 1851; // latitud a metros
                    longitudmat = Math.toDegrees(longitud) * 1851;

                    distancia =(Math.sqrt((latitudmat - latitudMaps) * (latitudmat - latitudMaps) + (longitudmat - longitudMaps) * (longitudmat - longitudMaps)));
                    //textView.setText(String.valueOf(distancia));
                }


            } else Toast.makeText(ConfigActivity.this, "No hay red habilitada", Toast.LENGTH_SHORT).show();

            ////////////////////////////////////////////////

            }

*/

            return null;
        }
    }


    public void bcambiarubicacion(View view) {

        Intent intent = new Intent(ConfigActivity.this, MapsActivity.class);
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void bactualizar(View view) {
        etradio= (EditText) findViewById(R.id.etradio);
        radio =  Integer.parseInt(etradio.getText().toString());
        Bundle bundle = new Bundle();

        bundle.putDouble("longitud",longitud);
        bundle.putDouble("latitud",latitud);
        bundle.putInt("radio",radio);
        bundle.putDouble("longitudMaps",longitudMaps);
        bundle.putDouble("latitudMaps",latitudMaps);

        Intent intent = new Intent(ConfigActivity.this,ServicioDespetador.class);
        intent.putExtras(bundle);
        this.startService(intent);
        textView.setText(String.valueOf(distancia ));











/*
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } // fallo localizacion
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 200, locationlistener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 200, locationlistener);


        // getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnabled ) {
            if (locationManager != null){
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if (location != null ) {

                //location = lfyl.getLocation();
                latitud = location.getLatitude();
                longitud = location.getLongitude();
            }

        } else if(isNetworkEnabled){

            if (locationManager != null){

                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (location != null ) {

                //location = lfyl.getLocation();
                latitud = location.getLatitude();
                longitud = location.getLongitude();
            }


        } else {

            Toast.makeText(ConfigActivity.this, "No hay red habilitada", Toast.LENGTH_SHORT).show();
        }



        double latitudmat = Math.toDegrees(latitud) * 1851; // latitud a metros
        double longitudmat = Math.toDegrees(longitud) * 1851;

        distancia =(Math.sqrt((latitudmat - latitudMaps) * (latitudmat - latitudMaps) + (longitudmat - longitudMaps) * (longitudmat - longitudMaps)));
        textView.setText(String.valueOf(distancia));
*/
    }




}



