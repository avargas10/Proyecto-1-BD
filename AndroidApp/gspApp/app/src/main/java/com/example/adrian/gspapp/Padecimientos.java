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

import com.example.adrian.gspapp.Tools.Connection;

import org.json.JSONArray;
import org.json.JSONException;

public class Padecimientos extends Fragment {
    FloatingActionButton fab;
    private JSONArray jArray=new JSONArray();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Padecimientos");
        fab=(FloatingActionButton)getView().findViewById(R.id.fabButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), nuevoPadecimiento.class);
                startActivity(intent);
            }
        });
        try {
            jArray= Connection.getInstance().getPadecimientos(MainActivity.clientInfo.getInt("Cedula"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("LISTA PADECIMIENTOS: "+jArray.toString());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_padecimientos, container, false);
    }

}
