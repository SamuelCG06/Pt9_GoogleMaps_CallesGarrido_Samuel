package com.example.pt9_googlemaps_calles_samuel;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

public class MapsCiudadActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps_ciudad);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fab = findViewById(R.id.fab);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        fab.setOnClickListener(v -> pedirCiudad());
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void pedirCiudad() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Introduce ciudad");

        EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Buscar", (dialog, which) -> {

            String ciudad = input.getText().toString();

            try {
                Geocoder geocoder = new Geocoder(this);
                List<Address> lista = geocoder.getFromLocationName(ciudad, 1);

                if (!lista.isEmpty()) {
                    Address addr = lista.get(0);
                    LatLng pos = new LatLng(addr.getLatitude(), addr.getLongitude());

                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(pos).title(ciudad));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
                } else {
                    Toast.makeText(this, "Ciudad no encontrada", Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                Toast.makeText(this, "Error en Geocoder", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }
}