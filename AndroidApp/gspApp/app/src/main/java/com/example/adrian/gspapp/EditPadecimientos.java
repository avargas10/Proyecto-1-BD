package com.example.adrian.gspapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adrian.gspapp.Tools.Config;
import com.example.adrian.gspapp.Tools.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditPadecimientos extends AppCompatActivity {

    EditText nombre, fecha, descrip;
    Button addPad;
    private int idCliente;
    private DatePickerDialog datePickerDialog;
    public static List<String> allNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_padecimientos);

        nombre=(EditText) findViewById(R.id.txtUpdateNombrePad);
        fecha=(EditText) findViewById(R.id.txtUpdateFechaPad);
        descrip=(EditText) findViewById(R.id.txtUpdateDescripPad);
        addPad=(Button) findViewById(R.id.btnUpdatePad);

        addPad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    JSONObject pad= new JSONObject();
                    pad.put("idPadecimiento",Config.currentPad);
                    pad.put("idUsuario", MainActivity.clientInfo.getInt("Cedula"));
                    pad.put("Fecha", fecha.getText().toString());
                    pad.put("Nombre", nombre.getText().toString());
                    pad.put("Descripcion", descrip.getText().toString());
                    System.out.println("DESCRIPCION: "+descrip.getText().toString());
                    System.out.println("JSON: "+pad.toString());
                    Connection.getInstance().UpdatePad(pad);
                    Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG);
                    finish();

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        try {
            getPadecimientos();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getPadecimientos() throws JSONException {
        JSONObject objeto = Connection.getInstance().getPadbyId(Config.currentPad);
        nombre.setText(objeto.getString("Nombre"));
        fecha.setText(objeto.getString("Fecha"));
        descrip.setText(objeto.getString("Descripcion"));
    }
}
