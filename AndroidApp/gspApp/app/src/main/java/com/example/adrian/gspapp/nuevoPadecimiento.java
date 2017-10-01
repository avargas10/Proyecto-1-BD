package com.example.adrian.gspapp;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adrian.gspapp.R;
import com.example.adrian.gspapp.Tools.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class nuevoPadecimiento extends AppCompatActivity {
    EditText nombre, fecha, descrip;
    Button addPad;
    private int idCliente;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_padecimiento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registrar Padecimiento");

        nombre=(EditText) findViewById(R.id.txtNombrePad);
        fecha=(EditText) findViewById(R.id.txtFechaPad);
        descrip=(EditText) findViewById(R.id.txtDescripPad);
        addPad=(Button) findViewById(R.id.btnRegPad);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(nuevoPadecimiento.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                fecha.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        addPad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    JSONObject pad= new JSONObject();
                    pad.put("idUsuario", MainActivity.clientInfo.getInt("Cedula"));
                    pad.put("Nombre", nombre.getText());
                    pad.put("Fecha", fecha.getText());
                    pad.put("Descripcion", descrip.getText());
                    System.out.println("DESCRIPCION: "+descrip.getText().toString());
                    System.out.println("JSON: "+pad.toString());
                   if(Connection.getInstance().regPadecimiento(pad)){
                        Toast.makeText(getApplicationContext(),"Padecimiento Agregado Correctamente.", Toast.LENGTH_LONG).show();
                       nombre.setText("");
                       fecha.setText("");
                       descrip.setText("");
                      // Padecimientos.allNames.add(pad.getString("Nombre") + " - " +pad.get("Fecha").toString()+" - " +
                        //       pad.getString("Descripcion") );
                       Padecimientos.allNames.clear();
                    }else{
                        Toast.makeText(getApplicationContext(),"Error al agregar el Padecimiento.", Toast.LENGTH_LONG).show();

                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
