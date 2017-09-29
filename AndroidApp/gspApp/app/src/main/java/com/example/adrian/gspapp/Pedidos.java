package com.example.adrian.gspapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.adrian.gspapp.Tools.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pedidos extends Fragment {

    JSONArray dataProducts;
    ListView list;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = (ListView) getView().findViewById(R.id.list);
        getActivity().setTitle("Pedidos");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_pedidos, container, false);
    }

    private void getProducts() throws JSONException {
        dataProducts = Connection.getInstance().getProductos();
        List<String> allProducts = new ArrayList<String>();
        for(int i=0; i<dataProducts.length();i++){
            JSONObject objeto= (JSONObject) dataProducts.get(i);
            allProducts.add(objeto.getString("Nombre"));
        }
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<String>
                (this.getContext(), android.R.layout.simple_list_item_1, allProducts );

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_list_item_1);

        list.setAdapter(dataAdapter);
    }

}
