package com.example.adrian.gspapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class nuevaReceta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_receta);
        getSupportActionBar().setTitle("Registrar Receta");
    }
}
