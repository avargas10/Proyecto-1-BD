package com.example.adrian.gspapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

    }

    private void getProducts() throws JSONException {

        CustomList adapter = new
                CustomList(this, Config.allProducts, Config.allimg,Config.precios,Config.prescription);
        ShoppingList = (ListView)findViewById(R.id.Shoppinglist);
        ShoppingList.setAdapter(adapter);

    }
}
