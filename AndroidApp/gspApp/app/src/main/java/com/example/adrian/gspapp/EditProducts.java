package com.example.adrian.gspapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

public class EditProducts extends AppCompatActivity {

    public static ListView editlist;
    private JSONArray dataPedidos;
    private JSONObject generaldata;
    ListView sucursales;
    public static ProductsList adapter;
    private JSONArray dataSucursales;
    EditText fecha;
    EditText telefono;
    Button submit;
    int sucursal = 1;
    String encodedbyte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);
        editlist = (ListView) findViewById(R.id.EditList);
        sucursales = (ListView) findViewById(R.id.Edit_listaSucursales);
        submit = (Button) findViewById(R.id.Edit_Submit);
        fecha = (EditText)findViewById(R.id.Edit_FechaRecojo);
        telefono  = (EditText)findViewById(R.id.Edit_Telefono);

        sucursales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sucursal = position + 1;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                UpdatePedido();
            }
        });


        try {
            getProducts();
            getSucursales();
            getGeneral();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void UpdatePedido() {
        try{
            if(telefono.getText().toString().isEmpty() || fecha.getText().toString().isEmpty()){
                Toast.makeText(this, "Please fill all the blank spaces", Toast.LENGTH_SHORT).show();
            }
            else {
                Connection.getInstance().deleteDetalle(generaldata.getInt("idPedido"));

                for(int i = 0; i < Config.selectedallrelation.size() ; i++){
                    JSONObject pad = new JSONObject();
                    pad.put("idProducto", Config.selectedallrelation.get(i));
                    pad.put("idPedido", generaldata.getInt("idPedido"));
                    pad.put("idCantidad", Config.selectedcant.get(i));
                    Connection.getInstance().regDetalle(pad);
                }

                JSONObject pad = new JSONObject();
                pad.put("idPedido",generaldata.getInt("idPedido"));
                pad.put("sucursalRecojo", sucursal);
                pad.put("idCliente", Config.ClientLogged.get("Cedula"));
                pad.put("horaRecojo", fecha.getText());
                pad.put("Telefono", telefono.getText());
                pad.put("Imagen", encodedbyte);
                pad.put("Estado", generaldata.getInt("Estado"));
                Connection.getInstance().UpdatePedido(pad);

            }

            Config.selectedallProducts.clear();
            Config.selectedallrelation.clear();
            Config.selectedallimg.clear();
            Config.selectedprecios.clear();
            Config.selectedprescription.clear();
            Config.selectedidproducto.clear();
            Config.selectedcant.clear();
            Toast.makeText(this,"Order Updated",Toast.LENGTH_LONG).show();
            finish();

        }catch(Exception e){
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
    public void getGeneral() throws JSONException {
        generaldata = Connection.getInstance().getPedidobyIdPedido(Config.currentorder);
        fecha.setText(generaldata.getString("horaRecojo"));
        telefono.setText(generaldata.getString("Telefono"));
        sucursal = generaldata.getInt("sucursalRecojo");
        encodedbyte = generaldata.getString("Imagen");
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

    private void getSucursales() throws JSONException {
        dataSucursales =Connection.getInstance().getSucursales();

        List<String> allNames = new ArrayList<String>();
        for (int i = 0; i < dataSucursales.length(); i++) {
            JSONObject objeto = (JSONObject) dataSucursales.get(i);
            allNames.add(objeto.getString("Nombre") + " - " + objeto.getString("detalleDireccion") );
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, allNames);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        sucursales.setAdapter(dataAdapter);

    }
}
