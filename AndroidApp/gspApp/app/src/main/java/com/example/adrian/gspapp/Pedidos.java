package com.example.adrian.gspapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.adrian.gspapp.Tools.Connection;
import com.example.adrian.gspapp.Tools.CustomList;

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
        try {
            getProducts();
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

        for(int i=0; i<dataProducts.length();i++){
            JSONObject objeto= (JSONObject) dataProducts.get(i);
            allProducts.add(objeto.getString("Nombre"));
            byte[] decodedString = Base64.decode(objeto.getString("Image"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            allimg.add(decodedByte);
        }

        CustomList adapter = new
                CustomList((Activity) this.getContext(), allProducts, allimg);
        list=(ListView)getView().findViewById(R.id.list);
        list.setAdapter(adapter);

    }

}
