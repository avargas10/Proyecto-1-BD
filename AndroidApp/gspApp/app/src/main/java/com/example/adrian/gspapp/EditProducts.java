package com.example.adrian.gspapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adrian.gspapp.Tools.Config;
import com.example.adrian.gspapp.Tools.Connection;
import com.example.adrian.gspapp.Tools.CustomList;
import com.example.adrian.gspapp.Tools.ProductsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditProducts extends AppCompatActivity {

    ListView editlist;
    private JSONArray dataPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);
        editlist = (ListView) findViewById(R.id.EditList);
    }

    private void getProducts() throws JSONException {

        CustomList adapter = new
                ProductsList((Activity) this, allProducts, allimg, prescription,Config.ADD,precios);
        editlist.setAdapter(adapter);

    }
}
