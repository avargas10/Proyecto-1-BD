package com.example.adrian.gspapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adrian.gspapp.Tools.Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {
    EditText username, name, pass, pApellido, sApellido, cedula;
    Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Registration");

        username = (EditText) findViewById(R.id.txtUsername);
        pass = (EditText) findViewById(R.id.txtPassword);
        name = (EditText) findViewById(R.id.txtNombre);
        pApellido = (EditText) findViewById(R.id.txtApellido1);
        sApellido = (EditText) findViewById(R.id.txtApellido2);
        cedula = (EditText) findViewById(R.id.txtCedula);
        registro = (Button) findViewById(R.id.btnSignup);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject data = new JSONObject();
                try {
                    data.put("Cedula", Integer.parseInt(cedula.getText().toString()));
                    data.put("Nombre", name.getText().toString());
                    data.put("pApellido", pApellido.getText().toString());
                    data.put("sApellido", sApellido.getText().toString());
                    data.put("Password", pass.getText().toString());
                    data.put("Username", username.getText().toString());
                    data.put("Nacimiento", null);
                    data.put("Direccion", null);
                    if(Connection.getInstance().registroCliente(data)){
                        Toast.makeText(getApplicationContext(),"Usuario registrado con exito", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        pass.setText("");
                        name.setText("");
                        pApellido.setText("");
                        sApellido.setText("");
                        cedula.setText("");
                    }else{
                        Toast.makeText(getApplicationContext(),"No se ha podido registrar el usuario, intentelo mas tarde.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}