package com.example.adrian.gspapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ListView;

import com.example.adrian.gspapp.Tools.Connection;
import com.example.adrian.gspapp.Tools.CustomList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {

    ListView ShoppingList;
    private JSONArray dataShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

    }

    private void getProducts() throws JSONException {
        dataShopping = Connection.getInstance().getProductos();
        List<String> allProducts = new ArrayList<String>();
        List<Bitmap> allimg = new ArrayList<>();

        for(int i=0; i<dataShopping.length();i++){
            JSONObject objeto= (JSONObject) dataShopping.get(i);
            allProducts.add(objeto.getString("Nombre"));
            byte[] decodedString = Base64.decode(objeto.getString("Image"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            allimg.add(decodedByte);
        }

        CustomList adapter = new
                CustomList(this, allProducts, allimg);
        ShoppingList = (ListView)findViewById(R.id.Shoppinglist);
        ShoppingList.setAdapter(adapter);

    }
}
