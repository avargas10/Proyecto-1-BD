package com.example.adrian.gspapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.gspapp.Tools.Config;
import com.example.adrian.gspapp.Tools.Connection;
import com.example.adrian.gspapp.Tools.ProductsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditProducts extends AppCompatActivity {

    ListView editlist;
    private JSONArray dataPedidos;
    public static ProductsList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);
        editlist = (ListView) findViewById(R.id.EditList);

        editlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Config.selectedallProducts.remove(position);
                Config.selectedallrelation.remove(position);
                Config.selectedallimg.remove(position);
                Config.selectedprecios.remove(position);
                Config.selectedprescription.remove(position);
                Config.selectedidproducto.remove(position);
                Config.selectedcant.remove(position);
                adapter.notifyDataSetChanged();


            }
        });

        try {
            getProducts();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Config.selectedallProducts.clear();
        Config.selectedallrelation.clear();
        Config.selectedallimg.clear();
        Config.selectedprecios.clear();
        Config.selectedprescription.clear();
        Config.selectedidproducto.clear();
        Config.selectedcant.clear();
        finish();
    }

    private void getProducts() throws JSONException {
        dataPedidos = Connection.getInstance().getDetallebyId(Config.currentorder);

        for(int x = 0 ; x < dataPedidos.length();x++){
            JSONObject objeto= (JSONObject) dataPedidos.get(x);
            Config.selectedallrelation.add(objeto.getInt("idProducto"));
            Config.selectedcant.add(objeto.getInt("Cantidad"));
        }
        for(int i = 0; i < Config.selectedallrelation.size() ; i++){
            JSONObject objeto = Connection.getInstance().getProductobyId(Config.selectedallrelation.get(i));
            Config.selectedallProducts.add(objeto.getString("Nombre"));
            byte[] decodedString = Base64.decode(objeto.getString("Image"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            Config.selectedallimg.add(decodedByte);
            Config.selectedidproducto.add(objeto.getInt("idProducto"));
            if(objeto.getInt("reqPrescripcion") == 1){
                Config.selectedprescription.add("Require prescription");
            }
            else{
                Config.selectedprescription.add("");
            }
        }

        adapter = new
                ProductsList((Activity) this, Config.selectedallProducts, Config.selectedallimg, Config.selectedprescription,Config.ADD,Config.selectedprecios,Config.selectedcant);
        editlist.setAdapter(adapter);

    }
}
