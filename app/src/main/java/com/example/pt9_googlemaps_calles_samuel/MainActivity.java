package com.example.pt9_googlemaps_calles_samuel;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnLatLng).setOnClickListener(v ->
                startActivity(new Intent(this, MapsLatLngActivity.class)));

        findViewById(R.id.btnCiudad).setOnClickListener(v ->
                startActivity(new Intent(this, MapsCiudadActivity.class)));

        findViewById(R.id.btnUbicacion).setOnClickListener(v ->
                startActivity(new Intent(this, MapsMiUbicacionActivity.class)));
    }
}
