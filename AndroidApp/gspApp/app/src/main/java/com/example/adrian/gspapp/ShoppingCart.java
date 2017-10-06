package com.example.adrian.gspapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adrian.gspapp.Tools.Config;
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
    CustomList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ShoppingList = (ListView)findViewById(R.id.Shoppinglist);
        ShoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Config.allProducts.remove(position);
                Config.allimg.remove(position);
                Config.precios.remove(position);
                Config.prescription.remove(position);
                Config.idproducto.remove(position);
                Config.cantidad.remove(position);
                adapter.notifyDataSetChanged();

            }
        });

        try {
            getProducts();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getProducts() throws JSONException {

        adapter = new
                CustomList(this, Config.allProducts, Config.allimg,Config.prescription,Config.DELETE,Config.precios);
        ShoppingList.setAdapter(adapter);

    }
}
