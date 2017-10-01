package com.example.adrian.gspapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.adrian.gspapp.Tools.Connection;
import com.example.adrian.gspapp.Tools.CustomList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Pedidos extends Fragment {

    JSONArray dataProducts;
    ListView list,sucursales;
    DatePickerDialog datePickerDialog;
    EditText recojo;
    FloatingActionButton btncart;
    private JSONArray dataSucursales;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = (ListView) getView().findViewById(R.id.list);
        sucursales = (ListView) getView().findViewById(R.id.listaSucursales);
        recojo = (EditText) getView().findViewById(R.id.FechaRecojo);
        btncart = (FloatingActionButton)getView().findViewById(R.id.ShoppingCart);
        recojo.setOnClickListener(new View.OnClickListener() {
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
                                recojo.setText(year + "-"
                                        + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ShoppingCart.class));
            }
        });
        getActivity().setTitle("Pedidos");
        try {
            getProducts();
            getSucursales();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_pedidos, container, false);
    }

    Integer[] imgid={
    };

    private void getProducts() throws JSONException {
        dataProducts = Connection.getInstance().getProductos();
        List<String> allProducts = new ArrayList<String>();
        List<Bitmap> allimg = new ArrayList<>();
        List<Integer> precios = new ArrayList<>();

        for(int i=0; i<dataProducts.length();i++){
            JSONObject objeto= (JSONObject) dataProducts.get(i);
            allProducts.add(objeto.getString("Nombre"));
            byte[] decodedString = Base64.decode(objeto.getString("Image"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            allimg.add(decodedByte);
            precios.add(objeto.getInt("Precio"));
        }

        CustomList adapter = new
                CustomList((Activity) this.getContext(), allProducts, allimg, precios);
        list=(ListView)getView().findViewById(R.id.list);
        list.setAdapter(adapter);

    }
    private void getSucursales() throws JSONException {
        dataSucursales =Connection.getInstance().getSucursales();

        List<String> allNames = new ArrayList<String>();
        for (int i = 0; i < dataSucursales.length(); i++) {
            JSONObject objeto = (JSONObject) dataSucursales.get(i);
            allNames.add(objeto.getString("Nombre") + " - " + objeto.getString("detalleDireccion") );
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, allNames);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        sucursales.setAdapter(dataAdapter);

    }


}
