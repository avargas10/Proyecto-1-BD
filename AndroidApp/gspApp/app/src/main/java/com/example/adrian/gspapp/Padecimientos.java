package com.example.adrian.gspapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adrian.gspapp.Tools.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Padecimientos extends Fragment {
    FloatingActionButton fab;
    public static JSONArray jArray=new JSONArray();
    public static ListView lista;
   public static List<String> allNames = new ArrayList<String>();
    public static  ArrayAdapter<String> dataAdapter;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Padecimientos");
        fab=(FloatingActionButton)getView().findViewById(R.id.fabButton);
        lista=(ListView) getView().findViewById(R.id.listaPad);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), nuevoPadecimiento.class);
                startActivity(intent);
            }
        });
        allNames.clear();
        try {
            jArray= Connection.getInstance().getPadecimientos(MainActivity.clientInfo.getInt("Cedula"));
            getPadecimientos(jArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_padecimientos, container, false);
    }

    public void getPadecimientos(JSONArray data) throws JSONException {
        System.out.println("PADECIMIENTOS: "+data.toString());
         dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, allNames);
        for (int i = 0; i < data.length(); i++) {
            JSONObject objeto = new JSONObject( data.get(i).toString());
            allNames.add(objeto.getString("Nombre") + " - " +objeto.get("Fecha").toString().split("T")[0]+" - " +
                    objeto.getString("Descripcion") );
        }
        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        lista.setAdapter(dataAdapter);

    }

}
