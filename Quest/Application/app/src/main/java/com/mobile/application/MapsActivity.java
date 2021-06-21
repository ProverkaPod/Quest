package com.mobile.application;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobile.application.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

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
     * Метод обратной связи карты Google. Вызывается когда объект карты создан и готов к использованию.
     *
     * @param googleMap Экземпляр карты Google
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(1.0f);
        mMap.setMaxZoomPreference(30.0f);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(getIntent().getLongExtra("gps_lat", 0), getIntent().getLongExtra("gps_long", 0));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Маркер"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}