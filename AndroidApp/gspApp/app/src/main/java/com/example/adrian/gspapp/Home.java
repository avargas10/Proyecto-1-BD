package com.example.adrian.gspapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adrian.gspapp.Tools.Config;
import com.example.adrian.gspapp.Tools.Connection;
import com.example.adrian.gspapp.Tools.CustomList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    ListView pedidosList;
    private JSONArray dataPedidos,dataSucursales;
    CustomList adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pedidos");
        pedidosList = (ListView)getView().findViewById(R.id.pedidoslist);
        try {
            getPedidos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pedidosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Config.currentorder = ((JSONObject)dataPedidos.get(position)).getInt("idPedido");
                    startActivity(new Intent(getContext(),EditProducts.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    private void getPedidos() throws JSONException {
        dataPedidos = Connection.getInstance().getPedidobyId(Config.ClientLogged.getInt("Cedula"));

        List<String> allNames = new ArrayList<String>();
        for (int i = 0; i < dataPedidos.length(); i++) {
            JSONObject objeto = (JSONObject) dataPedidos.get(i);
            JSONObject sucursal = Connection.getInstance().getSucursalbyId(objeto.getInt("sucursalRecojo")).getJSONObject(0);
            allNames.add("Pedido #" + objeto.getString("idPedido") + " - " + sucursal.getString("Nombre") );
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, allNames);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        pedidosList.setAdapter(dataAdapter);

    }

}
