package com.example.pt9_googlemaps_calles_samuel;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsLatLngActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maps_latlng);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        findViewById(R.id.fab).setOnClickListener(v -> pedirCoordenadas());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void pedirCoordenadas() {

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        EditText lat = new EditText(this);
        lat.setHint("Latitud");

        EditText lng = new EditText(this);
        lng.setHint("Longitud");

        layout.addView(lat);
        layout.addView(lng);

        new AlertDialog.Builder(this)
                .setTitle("Introduce coordenadas")
                .setView(layout)
                .setPositiveButton("Ir", (d, w) -> {
                    try {
                        double latitud = Double.parseDouble(lat.getText().toString());
                        double longitud = Double.parseDouble(lng.getText().toString());

                        LatLng pos = new LatLng(latitud, longitud);
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(pos));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));

                    } catch (Exception e) {
                        Toast.makeText(this, "Coordenadas inválidas", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}