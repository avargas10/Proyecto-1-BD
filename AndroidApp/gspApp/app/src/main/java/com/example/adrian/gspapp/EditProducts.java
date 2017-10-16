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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EditProducts extends AppCompatActivity {

    public static ListView editlist;
    private JSONArray dataPedidos;
    private JSONObject generaldata;
    ListView sucursales;
    public static ProductsList adapter;
    private static final int CAMERA_REQUEST = 1888;
    FloatingActionButton btndelete;
    private JSONArray dataSucursales;
    EditText fecha;
    EditText telefono;
    Button submit;
    AlertDialog.Builder builder;
    int sucursal = 1;
    String encodedbyte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Order");
        setContentView(R.layout.activity_edit_products);
        editlist = (ListView) findViewById(R.id.EditList);
        sucursales = (ListView) findViewById(R.id.Edit_listaSucursales);
        submit = (Button) findViewById(R.id.Edit_Submit);
        fecha = (EditText)findViewById(R.id.Edit_FechaRecojo);
        telefono  = (EditText)findViewById(R.id.Edit_Telefono);

        btndelete = (FloatingActionButton)findViewById(R.id.btnDelete);

        sucursales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sucursal = position + 1;
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteOrder();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            encodedbyte = Base64.encodeToString(byteArray, Base64.DEFAULT);
            if(encodedbyte != null) {
                UpdatePedido();
            }
        }
    }

    private boolean RequirePrescription() {
        for(int i = 0 ; i < Config.selectedprescription.size() ; i++){
            if(Config.selectedprescription.get(i).equalsIgnoreCase("Require prescription")){
                return true;
            }
        }
        return false;
    }


    private void DeleteOrder() {
        try {
            Connection.getInstance().deletePedido(generaldata.getInt("idPedido"));
            Config.selectedallProducts.clear();
            Config.selectedallrelation.clear();
            Config.selectedallimg.clear();
            Config.selectedprecios.clear();
            Config.selectedprescription.clear();
            Config.selectedidproducto.clear();
            Config.selectedcant.clear();
            Toast.makeText(this,"Order Deleted",Toast.LENGTH_LONG).show();
            finish();
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
                for(int i = 0; i < Config.selectedallrelation.size() ; i++){
                    JSONObject pad1 = new JSONObject();
                    pad1.put("idProducto", Config.selectedallrelation.get(i));
                    pad1.put("idPedido", generaldata.getInt("idPedido"));
                    pad1.put("Cantidad", Config.selectedcant.get(i));
                    pad1.put("idSucursal", sucursal);
                    Connection.getInstance().UpdateDetallePedido(pad1);
                }

                Connection.getInstance().deleteDetalle(generaldata.getInt("idPedido"));

                for(int i = 0; i < Config.selectedallrelation.size() ; i++){
                    JSONObject pad1 = new JSONObject();
                    pad1.put("idProducto", Config.selectedallrelation.get(i));
                    pad1.put("idPedido", generaldata.getInt("idPedido"));
                    pad1.put("Cantidad", Config.selectedcant.get(i));
                    pad1.put("idSucursal", sucursal);
                    Connection.getInstance().regDetalle(pad1);
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

    public void btnMethodUpdate(View view) {
        if(RequirePrescription()) {
            builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to change Prescription Image?")
                    .setTitle("Prescription Request");
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, CAMERA_REQUEST);

                            }
                        }
                    });

            builder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            UpdatePedido();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        else{
            UpdatePedido();
        }
    }
}
