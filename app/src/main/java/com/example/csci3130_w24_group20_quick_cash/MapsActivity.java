/**
 * This class represents the Maps activity of the application.
 * It displays a map with markers for job postings based on their addresses.
 */
package com.example.csci3130_w24_group20_quick_cash;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.BaseEmployeeActivity;
import com.example.csci3130_w24_group20_quick_cash.BaseEmployeeActivity.EmployeeFragments.JobDetailsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.csci3130_w24_group20_quick_cash.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<JobPosting> jobPostings;
    private ActivityMapsBinding binding;

    private HashMap<Marker, JobPosting> markerJobPostingHashMap;


    /**
     * Called when the activity is starting.
     * Sets up the layout and retrieves job postings data passed through intent.
     * Initializes the map fragment asynchronously.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("jobPostings")) {
            jobPostings = (List<JobPosting>) intent.getSerializableExtra("jobPostings");
            markerJobPostingHashMap = new HashMap<>();
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
     * we just add a marker near the location of each job posting.
     * @param googleMap The GoogleMap object representing the map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (jobPostings != null) {
            for (JobPosting job : jobPostings) {
                LatLng location = getLocationFromAddress(job.getJobCountry(), job.getJobCity(), job.getJobAddress());
                if (location != null) {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(job.getJobTitle()));
                    markerJobPostingHashMap.put(marker, job);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                }
            }
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    JobPosting job = markerJobPostingHashMap.get(marker);
                    if (job != null) {
                        openJobPostingDetailsFragment(job);
                    }
                    return true;
                }
            });
        }
        }

    private void openJobPostingDetailsFragment(JobPosting job) {
        Intent intent = new Intent(this, BaseEmployeeActivity.class);
        intent.putExtra("jobDetails", job);
        startActivity(intent);
    }



    /**
     * Retrieves latitude and longitude coordinates from an address string.
     * @param country The country of the address.
     * @param city The city of the address.
     * @param address The street address.
     * @return The LatLng object representing the location.
     */
    private LatLng getLocationFromAddress(String country, String city, String address) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> streetAddresses = geocoder.getFromLocationName(address + ", " + city + ", " + country, 1);
            if (!streetAddresses.isEmpty()) {
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
