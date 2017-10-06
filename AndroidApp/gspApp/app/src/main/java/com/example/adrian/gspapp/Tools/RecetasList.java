package com.example.adrian.gspapp.Tools;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adrian.gspapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Adrian on 06/10/2017.
 */

public class RecetasList extends ArrayAdapter<JSONObject> {
    Activity context;
    List<JSONObject> data;
    public RecetasList(Activity context, List data){
        super(context, R.layout.post_principal, data);
        this.context=context;
        this.data=data;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        try {
            LayoutInflater inflater = this.context.getLayoutInflater();
            View itemView = inflater.inflate(R.layout.post_principal, null, false);
            TextView txtTitle = (TextView) itemView.findViewById(R.id.titulo_postPrincipal);
            TextView txtDescripcion = (TextView) itemView.findViewById(R.id.descripcion_postPrincipal);
            txtTitle.setText( this.data.get(position).getString("titulo"));
            txtDescripcion.setText(this.data.get(position).getString("contenido"));

            return itemView;
        }catch (Exception e){
            e.printStackTrace();
        }
        return this.context.getLayoutInflater().inflate(R.layout.post_principal, null, true);
    }
}
