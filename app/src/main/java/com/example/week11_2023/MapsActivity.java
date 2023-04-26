package com.example.week11_2023;

import static android.location.LocationManager.GPS_PROVIDER;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.week11_2023.databinding.ActivityMapsBinding;

import java.security.Permission;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LocationManager lm;
    private LocationListener ls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        ls= new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
            Toast.makeText(MapsActivity.this,location.toString(),Toast.LENGTH_LONG).show();


                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                Toast.makeText(MapsActivity.this,"Hello ",Toast.LENGTH_LONG).show();
//40.95267208868221, 29.119481651180024
                // Add a marker in Sydney and move the camera
                Location lc=lm.getLastKnownLocation(GPS_PROVIDER);

                LatLng ticaret = new LatLng(lc.getLatitude(),lc.getLongitude() );
                mMap.addMarker(new MarkerOptions().position(ticaret).title("My Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ticaret,15));

            }
        };

        if(     (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                 != PackageManager.PERMISSION_GRANTED) )
        {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                                  Manifest.permission.ACCESS_COARSE_LOCATION},
                    1453 );
                return;
        }else
           lm.requestLocationUpdates(GPS_PROVIDER,5000,100,ls);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
if(requestCode==1453){
    if(grantResults.length>0 &&
            grantResults[0]== PackageManager.PERMISSION_GRANTED &&
            grantResults[1]==PackageManager.PERMISSION_GRANTED){
        if(     (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) )
        {
            lm.requestLocationUpdates(GPS_PROVIDER,5000,100,ls);

        }




    }
}

    }
}