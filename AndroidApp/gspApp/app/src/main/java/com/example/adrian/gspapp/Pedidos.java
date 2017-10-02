package com.example.adrian.gspapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

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
    Button submit;
    private JSONArray dataSucursales;
    private boolean reqpres = false;
    private static final int CAMERA_REQUEST = 1888;
    AlertDialog.Builder builder;
    static List<String> prescription;
    static List<String> allProducts;
    static List<Bitmap> allimg;
    static List<Integer> precios;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = (ListView) getView().findViewById(R.id.list);
        sucursales = (ListView) getView().findViewById(R.id.listaSucursales);
        recojo = (EditText) getView().findViewById(R.id.FechaRecojo);
        submit = (Button) getView().findViewById(R.id.Submit);
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
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(reqpres){
                    builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("You need to enter a prescription to continue")
                            .setTitle("Prescription Request");
                    final ImageButton input = new ImageButton(getContext());
                    input.setImageResource(R.drawable.camera);
                    builder.setView(input);
                    input.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, CAMERA_REQUEST);

                            }
                        }
                    });

                    builder.setPositiveButton("SEND",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int which) {
                                    Toast.makeText(getContext(),"Products List Sended", Toast.LENGTH_SHORT).show();

                                }
                            });

                    builder.setNegativeButton("CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{}
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Log.e("compability test......","entre a este metodo.......");

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
        prescription = new ArrayList<String>();
        allProducts = new ArrayList<String>();
        allimg = new ArrayList<>();
        precios = new ArrayList<>();

        for(int i=0; i<dataProducts.length();i++){
            JSONObject objeto= (JSONObject) dataProducts.get(i);
            allProducts.add(objeto.getString("Nombre"));
            byte[] decodedString = Base64.decode(objeto.getString("Image"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            allimg.add(decodedByte);
            precios.add(objeto.getInt("Precio"));
            if(objeto.getInt("reqPrescripcion") == 1){
                reqpres = true;
                prescription.add("Require prescription");
            }
            else{
                prescription.add("");
            }
        }

        CustomList adapter = new
                CustomList((Activity) this.getContext(), allProducts, allimg, precios, prescription);
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
