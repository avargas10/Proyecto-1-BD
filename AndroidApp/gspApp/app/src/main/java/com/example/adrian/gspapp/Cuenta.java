package com.example.adrian.gspapp;

import android.app.DatePickerDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adrian.gspapp.Tools.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Cuenta extends Fragment {
    EditText cedula, nombre, sApellido, pApellido, password, username, email, nacimiento, direccion;
    Spinner provincia, canton, distrito;
    TextView delete;
    Button update;
    DatePickerDialog datePickerDialog;
    private JSONArray dataProvincia, dataCanton, dataDistrito;
    private int idProvincia, idCanton, idDistrito;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Administrar Cuenta");

        cedula=getView().findViewById(R.id.editId);
        nombre=getView().findViewById(R.id.editName);
        pApellido=getView().findViewById(R.id.editSurname);
        sApellido=getView().findViewById(R.id.editsSurname);
        password=getView().findViewById(R.id.editPassword);
        username=getView().findViewById(R.id.editUsername);
        email=getView().findViewById(R.id.editEmail);
        nacimiento=getView().findViewById(R.id.editNacimiento);
        direccion=getView().findViewById(R.id.editDetalleDir);
        provincia=getView().findViewById(R.id.editSpinnerP);
        canton=getView().findViewById(R.id.editSpinnerC);
        distrito=getView().findViewById(R.id.editSpinnerD);
        delete=getView().findViewById(R.id.txtDeleteAccount);
        update=getView().findViewById(R.id.btnEditClient);

        try {
            cedula.setText(Integer.toString(MainActivity.clientInfo.getInt("Cedula")));
            nombre.setText(MainActivity.clientInfo.getString("Nombre"));
            pApellido.setText(MainActivity.clientInfo.getString("pApellido"));
            sApellido.setText(MainActivity.clientInfo.getString("sApellido"));
            username.setText(MainActivity.clientInfo.getString("Username"));
            email.setText(MainActivity.clientInfo.getString("Email"));
            nacimiento.setText(MainActivity.clientInfo.getString("Nacimiento").split("T")[0]);
            direccion.setText(MainActivity.clientDir.getString("Descripcion"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getContext(),
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
                            id=objeto.getInt("idCanton");
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
           // fillSpinners();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fillProvincia() throws JSONException {
        dataProvincia = Connection.getInstance().getDirections(1,0);

        List<String> allNames = new ArrayList<String>();
        for (int i = 0; i < dataProvincia.length(); i++) {
            JSONObject objeto = (JSONObject) dataProvincia.get(i);
            allNames.add(objeto.getString("Nombre"));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, allNames);

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
                (getContext(), android.R.layout.simple_spinner_item, allNames);

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
                (getContext(), android.R.layout.simple_spinner_item, allNames);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        distrito.setAdapter(dataAdapter);
        fillSpinners();

    }

    private void fillSpinners() throws JSONException {
        provincia.setSelection(MainActivity.clientDir.getInt("Provincia")-1);
        if(MainActivity.clientDir.getInt("Canton")%2==0){
            canton.setSelection(1);
        }else{
            canton.setSelection(0);
        }
        if(MainActivity.clientDir.getInt("Distrito")%2==0){
            distrito.setSelection(1);
        }else{
            distrito.setSelection(0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_cuenta, container, false);
    }
}
