package com.ug.air.sproutofinnovateapp.Activities;

import static com.ug.air.sproutofinnovateapp.Activities.LoansActivity.SHARED_PREFS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.ug.air.sproutofinnovateapp.R;

public class MapActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnMapReadyCallback {

    Spinner spinner;
    String finder, geo_point;
    ArrayAdapter<CharSequence> adapter1;
    LinearLayout linearLayout;
    GoogleMap mMap;
    FusedLocationProviderClient mFusedLocationProviderClient;
    LatLng latLng1, latLng2;
    Button btnCurrent, btnSelected;
    int value = 0;
    String lat;
    public static final String GEO_POINT_1 = "geo_point";
    public static final String GEO_POINT_2 = "geo_point_2";
    public static final String GEO_POINT_3 = "geo_point_3";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        spinner = findViewById(R.id.time);
        linearLayout = findViewById(R.id.buttons);
        btnCurrent = findViewById(R.id.current);
        btnSelected = findViewById(R.id.selected);

        adapter1 = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        btnCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latLng1 == null){
                    Toast.makeText(MapActivity.this, "Current Location not found", Toast.LENGTH_SHORT).show();
                }else {
                    lat = latLng1.latitude + ", " + latLng1.longitude;
                    saveData();
                }
            }
        });

        btnSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latLng2 == null){
                    Toast.makeText(MapActivity.this, "No place selected", Toast.LENGTH_SHORT).show();
                }else {
                    lat = latLng2.latitude + ", " + latLng2.longitude;
                    saveData();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        finder = parent.getItemAtPosition(position).toString();
        if (finder.equals("Select one")){
            linearLayout.setVisibility(View.GONE);
        }else {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                mMap.clear();
                latLng2 = new LatLng(latLng.latitude, latLng.longitude);
                mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });
    }

    private void getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        latLng1 = new LatLng(location.getLatitude(), location.getLongitude());
                        moveCamera(latLng1, 18f);
                    }else {
                        Toast.makeText(MapActivity.this, "Couldn't find your location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (SecurityException e){
            Log.e("TAG", "getDeviceLocation: "+ e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void saveData() {
        if (finder.equals("Applicant's Home")){
            editor.putString(GEO_POINT_1, lat);
        }
        else if (finder.equals("Applicant's Collateral")){
            editor.putString(GEO_POINT_2, lat);
        }
        else {
            editor.putString(GEO_POINT_3, lat);
        }

        editor.apply();

        startActivity(new Intent(MapActivity.this, LoanActivity.class));
    }
}