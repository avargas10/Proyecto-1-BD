package com.example.adrian.gspapp;

import android.app.DatePickerDialog;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adrian.gspapp.Tools.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Registro extends AppCompatActivity {
    EditText username, name, pass, pApellido, sApellido, cedula, nacimiento, email, direccionExacta;
    Button registro;
    Spinner provincia, canton, distrito;
    DatePickerDialog datePickerDialog;
    private JSONArray dataProvincia, dataCanton, dataDistrito;
    private int idProvincia, idCanton, idDistrito;

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
        nacimiento= (EditText)  findViewById(R.id.txtNacimiento);
        email = (EditText) findViewById(R.id.txtEmail);
        direccionExacta=(EditText)findViewById(R.id.txtDetalleDireccion);
        provincia=(Spinner)findViewById(R.id.spinProvincia);
        canton=(Spinner)findViewById(R.id.spinCanton);
        distrito=(Spinner)findViewById(R.id.spinDistrito);
        Connection.getInstance().getDirections(1,0);

        nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Registro.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                nacimiento.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject data = new JSONObject();
                JSONObject direccion =new JSONObject();
                try {
                    direccion.put("Provincia", idProvincia);
                    direccion.put("Canton", idCanton);
                    direccion.put("Distrito", idDistrito);
                    direccion.put("Descripcion", direccionExacta.getText());
                    data.put("Cedula", Integer.parseInt(cedula.getText().toString()));
                    data.put("Nombre", name.getText().toString());
                    data.put("pApellido", pApellido.getText().toString());
                    data.put("sApellido", sApellido.getText().toString());
                    data.put("Password", pass.getText().toString());
                    data.put("Username", username.getText().toString());
                    data.put("Email", email.getText());
                    data.put("Penalizacion", 0);
                    data.put("Nacimiento", nacimiento.getText());
                    data.put("Direccion", Connection.getInstance().registroDireccion(direccion).get("idDireccion"));
                    if(Connection.getInstance().registroCliente(data)){
                        Connection.getInstance().ActivationEmail(Integer.parseInt(cedula.getText().toString()));
                        Toast.makeText(getApplicationContext(),"Usuario registrado con exito", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        pass.setText("");
                        name.setText("");
                        pApellido.setText("");
                        sApellido.setText("");
                        cedula.setText("");
                        email.setText("");
                        nacimiento.setText("");
                        direccionExacta.setText("");
                    }else{
                        Toast.makeText(getApplicationContext(),"No se ha podido registrar el usuario, intentelo mas tarde.", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id=0;
                String name=provincia.getSelectedItem().toString();
                try {
                for(int a=0; a<dataProvincia.length();a++){
                    JSONObject objeto=(JSONObject)dataProvincia.get(a);
                    if(objeto.getString("Nombre").equals(name)){
                        id=objeto.getInt("idProvincia");
                        break;
                    }

                }
                idProvincia=id;
                fillCanton(id);
                } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        canton.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id=0;
                String name=canton.getSelectedItem().toString();
                try {
                    for(int a=0; a<dataCanton.length();a++){
                        JSONObject objeto=(JSONObject)dataCanton.get(a);
                        if(objeto.getString("Nombre").equals(name)){
                            id=objeto.getInt("idCanton");
                            break;
                        }

                    }
                    idCanton=id;
                    fillDistrito(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id=0;
                String name=distrito.getSelectedItem().toString();
                try {
                    for(int a=0; a<dataDistrito.length();a++){
                        JSONObject objeto=(JSONObject)dataDistrito.get(a);
                        if(objeto.getString("Nombre").equals(name)){
                            id=objeto.getInt("idDistrito");
                            break;
                        }

                    }
                    idDistrito=id;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        try {
            fillProvincia();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fillProvincia() throws JSONException {
        dataProvincia =Connection.getInstance().getDirections(1,0);

            List<String> allNames = new ArrayList<String>();
            for (int i = 0; i < dataProvincia.length(); i++) {
                JSONObject objeto = (JSONObject) dataProvincia.get(i);
                allNames.add(objeto.getString("Nombre"));
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, allNames);

            dataAdapter.setDropDownViewResource
                    (android.R.layout.simple_spinner_dropdown_item);

            provincia.setAdapter(dataAdapter);

    }

    private void fillCanton(int id) throws JSONException {

            dataCanton = Connection.getInstance().getDirections(2, id);
            List<String> allNames = new ArrayList<String>();
            for (int i = 0; i < dataCanton.length(); i++) {
                JSONObject objeto = (JSONObject) dataCanton.get(i);
                allNames.add(objeto.getString("Nombre"));
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, allNames);

            dataAdapter.setDropDownViewResource
                    (android.R.layout.simple_spinner_dropdown_item);

            canton.setAdapter(dataAdapter);

    }

    private void fillDistrito(int id) throws JSONException {

            dataDistrito = Connection.getInstance().getDirections(3, id);
            List<String> allNames = new ArrayList<String>();
            for (int i = 0; i < dataDistrito.length(); i++) {
                JSONObject objeto = (JSONObject) dataDistrito.get(i);
                allNames.add(objeto.getString("Nombre"));
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item, allNames);

            dataAdapter.setDropDownViewResource
                    (android.R.layout.simple_spinner_dropdown_item);

            distrito.setAdapter(dataAdapter);

    }

}