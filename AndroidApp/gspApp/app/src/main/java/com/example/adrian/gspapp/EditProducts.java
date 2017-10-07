package com.example.adrian.gspapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);
        editlist = (ListView) findViewById(R.id.EditList);
        try {
            getProducts();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getProducts() throws JSONException {
        Log.e("order",String.valueOf(Config.currentorder));
        dataPedidos = Connection.getInstance().getDetallebyId(Config.currentorder);
        ArrayList<Integer> allrelation = new ArrayList<Integer>();
        ArrayList<String> prescription = new ArrayList<String>();
        ArrayList<String> allProducts = new ArrayList<String>();
        ArrayList<Bitmap> allimg = new ArrayList<>();
        ArrayList<Integer> precios = new ArrayList<>();
        ArrayList<Integer> idproducto = new ArrayList<>();
        ArrayList<Integer> cant = new ArrayList<>();
        Log.e("pedidos",dataPedidos.toString());
        for(int x = 0 ; x < dataPedidos.length();x++){
            JSONObject objeto= (JSONObject) dataPedidos.get(x);
            allrelation.add(objeto.getInt("idProducto"));
            cant.add(objeto.getInt("Cantidad"));
            Log.e("objetos",objeto.toString());
        }
        for(int i = 0; i < allrelation.size() ; i++){
            JSONObject objeto = Connection.getInstance().getProductobyId(allrelation.get(i));
            Log.e("JSON producto",objeto.toString());
            allProducts.add(objeto.getString("Nombre"));
            byte[] decodedString = Base64.decode(objeto.getString("Image"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            allimg.add(decodedByte);
            idproducto.add(objeto.getInt("idProducto"));
            if(objeto.getInt("reqPrescripcion") == 1){
                prescription.add("Require prescription");
            }
            else{
                prescription.add("");
            }
        }

        ProductsList adapter = new
                ProductsList((Activity) this, allProducts, allimg, prescription,Config.ADD,precios,cant);
        editlist.setAdapter(adapter);

    }
}
