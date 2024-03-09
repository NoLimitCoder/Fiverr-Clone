package com.example.csci3130_w24_group20_quick_cash;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.csci3130_w24_group20_quick_cash.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<JobPosting>jobPostings;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("jobPostings")) {
            jobPostings = (List<JobPosting>) intent.getSerializableExtra("jobPostings");

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (jobPostings != null) {
            for (JobPosting job : jobPostings) {
                LatLng location = getLocationFromAddress(job.getJobCountry(), job.getJobCity(), job.getJobAddress());
                if (location != null) {
                    mMap.addMarker(new MarkerOptions().position(location).title(job.getJobTitle()));
                }
            }
        }
    }



    private LatLng getLocationFromAddress(String country, String city, String address) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> streetAddresses = geocoder.getFromLocationName(address + ", " + city + ", " + country, 1);
            if (!address.isEmpty()){
                double streetLatitude = streetAddresses.get(0).getLatitude();
                double streetLongitude = streetAddresses.get(0).getLongitude();
                return new LatLng(streetLatitude, streetLongitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
