package fr.projet.besafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyMapActivity extends AppCompatActivity {


    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private final int REQUEST_CODE = 111;
    private NetworkInfo networkInfo;
    private GoogleMap mMap;
    private double selectedLat, selectedLng;
    private List<Address> addresses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MyMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();


        } else {
            ActivityCompat.requestPermissions(MyMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);

        }

    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null){

                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            mMap = googleMap;
                            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());

                            MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("you're here");

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14));

                            googleMap.addMarker(markerOptions).showInfoWindow();

                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(@NonNull LatLng latLng) {
                                    CheckConnection();

                                    if (networkInfo.isConnected() && networkInfo.isAvailable()){

                                        selectedLat = latLng.latitude;
                                        selectedLng = latLng.longitude;

                                        GetAddress(selectedLat,selectedLng);

                                        Intent intent = new Intent(MyMapActivity.this, DetailAlerteBeSafeActivity.class);
                                        intent.putExtra("keyLat", selectedLat);
                                        intent.putExtra("keyLng", selectedLng);
                                        intent.putExtra("keyCity", addresses.get(0).getLocality());
                                        intent.putExtra("keyAddress", addresses.get(0).getAddressLine(0));
                                        startActivity(intent);


                                    }else{
                                        Toast.makeText(MyMapActivity.this,"Please Check Connection", Toast.LENGTH_LONG);
                                    }

                                }
                            });

                        }
                    });

                }


            }
        });


    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void CheckConnection(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = manager.getActiveNetworkInfo();
    }

    private void GetAddress(double mLat, double mLng){
        Geocoder geocoder = new Geocoder(MyMapActivity.this, Locale.getDefault());

        if (mLat != 0){
            try {
                addresses = geocoder.getFromLocation(mLat,mLng,1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses != null){
                String mAddress = addresses.get(0).getAddressLine(0);

                String city  = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getCountryCode();
                String knownName = addresses.get(0).getFeatureName();
                String dis = addresses.get(0).getSubAdminArea();

                if (mAddress != null){
                    MarkerOptions markerOptions = new MarkerOptions();

                    LatLng latLng = new LatLng(mLat, mLng);

                    markerOptions.position(latLng).title(mAddress);

                    mMap.addMarker(markerOptions).showInfoWindow();

                }else {
                    Toast.makeText(this,  "Something went wrong", Toast.LENGTH_SHORT).show();
                }


            }else {
                Toast.makeText(this, "LatLng null", Toast.LENGTH_LONG).show();
            }

        }

    }
}