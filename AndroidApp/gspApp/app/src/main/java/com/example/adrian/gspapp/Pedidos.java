package com.example.adrian.gspapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.adrian.gspapp.Tools.Config;
import com.example.adrian.gspapp.Tools.Connection;
import com.example.adrian.gspapp.Tools.CustomList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Pedidos extends Fragment {

    JSONArray dataProducts;
    ListView list, prescriptions;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    EditText recojo;
    FloatingActionButton btncart;
    Button submit;
    private JSONArray dataPrescription, dataSucursales, dataPxS;
    private boolean reqpres = false;
    private static final int CAMERA_REQUEST = 1888;
    AlertDialog.Builder builder;
    static List<String> prescription;
    static List<String> allProducts;
    static List<Bitmap> allimg;
    static List<Integer> precios;
    static List<Integer> available;
    static List<Integer> idproducto;
    static ArrayList<String> unavailable;
    static ArrayList<Integer> cant_error;
    ArrayList<Integer> allrelation;
    String encodedprescription = null;
    static int sucursal = 0;
    EditText fecha;
    EditText telefono;
    int numpedido;
    private ArrayList<Integer> Recetasbyid;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Orders");
        list = (ListView) getView().findViewById(R.id.list);
        prescriptions = (ListView) getView().findViewById(R.id.listaPrescriptions);
        recojo = (EditText) getView().findViewById(R.id.FechaRecojo);
        submit = (Button) getView().findViewById(R.id.Submit);
        fecha = (EditText) getView().findViewById(R.id.FechaRecojo);
        telefono = (EditText) getView().findViewById(R.id.Telefono);
        btncart = (FloatingActionButton) getView().findViewById(R.id.ShoppingCart);
        final AlertDialog.Builder buildersuc = new AlertDialog.Builder(getContext());
        buildersuc.setMessage("Select the store where you want to buy to continue")
                .setTitle("Store Select");
        final ListView input = new ListView(getContext());

        ArrayAdapter<String> dataAdapter = null;
        try {
            dataAdapter = new ArrayAdapter<String>
                    (getContext(), android.R.layout.simple_list_item_1, listSucursales());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        input.setAdapter(dataAdapter);

        buildersuc.setView(input);

        buildersuc.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = buildersuc.create();
        dialog.show();
        input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    sucursal = position + 1;
                    getProducts();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // selected item
                String selectedproduct = ((TextView) view.findViewById(R.id.txtProductoRecetas)).getText().toString();
                ImageView imgview = (ImageView) view.findViewById(R.id.img2);
                imgview.setDrawingCacheEnabled(true);
                Bitmap selectedimg = Bitmap.createBitmap(imgview.getDrawingCache());
                int selectedprice = Integer.parseInt(((TextView) view.findViewById(R.id.txtCodigoProductoRecetas)).getText().toString());
                String selectedpres = ((TextView) view.findViewById(R.id.pres2)).getText().toString();
                final Spinner sp = (Spinner) view.findViewById(R.id.idcant);
                int cant = Integer.parseInt(sp.getSelectedItem().toString());

                Config.allProducts.add(selectedproduct);
                Config.allimg.add(selectedimg);
                Config.precios.add(selectedprice);
                Config.prescription.add(selectedpres);
                Config.idproducto.add(idproducto.get(position));
                Config.cantidad.add(cant);

                Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();


            }
        });
        prescriptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                JSONArray products = Connection.getInstance().getProductos(Recetasbyid.get(position));
                for (int i = 0; i < products.length(); i++) {
                    try {
                        JSONObject object = products.getJSONObject(i);
                        JSONObject prod = Connection.getInstance().getProductobyId(object.getInt("idMedicamento"));
                        Config.allProducts.add(prod.getString("Nombre"));

                        ImageView imgview = (ImageView) view.findViewById(R.id.img2);
                        imgview.setDrawingCacheEnabled(true);
                        Bitmap selectedimg = Bitmap.createBitmap(imgview.getDrawingCache());
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        selectedimg.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        encodedprescription = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        byte[] decodedString = Base64.decode(prod.getString("Image"), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        Config.allimg.add(decodedByte);
                        try {
                            JSONObject prodsuc = Connection.getInstance().getProductoSucursal(sucursal, object.getInt("idMedicamento"));
                            Config.precios.add(prodsuc.getInt("Precio"));
                        } catch (Exception e) {
                            Config.precios.add(0);
                        }
                        Config.prescription.add("PRESCRIPTED");
                        Config.idproducto.add(object.getInt("idMedicamento"));
                        Config.cantidad.add(1);
                    } catch (JSONException e) {
                        Log.e("falla", "operation incomplete");
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();

            }
        });

        recojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String txt = recojo.getText().toString();
                                if (String.valueOf(i).length() == 1 && String.valueOf(i1).length() == 1) {
                                    recojo.setText(txt + "T0" + String.valueOf(i) + ":0" + String.valueOf(i1) + ":" + "00");
                                } else if (String.valueOf(i).length() == 1 && String.valueOf(i1).length() == 2) {
                                    recojo.setText(txt + "T0" + String.valueOf(i) + ":" + String.valueOf(i1) + ":" + "00");
                                } else if (String.valueOf(i).length() == 2 && String.valueOf(i1).length() == 1) {
                                    recojo.setText(txt + "T" + String.valueOf(i) + ":0" + String.valueOf(i1) + ":" + "00");
                                } else {
                                    recojo.setText(txt + "T" + String.valueOf(i) + ":" + String.valueOf(i1) + ":" + "00");
                                }

                            }
                        }, 0, 0, true);
                timePickerDialog.show();
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
                startActivity(new Intent(getContext(), ShoppingCart.class));
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if (RequirePrescription()) {
                    builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("You need to enter a prescription to continue")
                            .setTitle("Prescription Request");
                    builder.setPositiveButton("SEND",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                                        startActivityForResult(takePictureIntent, CAMERA_REQUEST);

                                    }
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
                } else {
                    submitresult();
                }
            }
        });
        try {
            getPrescription();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void submitresult() {
        if (!Config.allProducts.isEmpty()) {
            try {
                if (IsProductAvaible()) {
                    if (telefono.getText().toString().isEmpty() || fecha.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Please fill all the blank spaces", Toast.LENGTH_SHORT).show();
                    } else {
                        regPedido();
                        regDetalle();
                        cleanVariables();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("One or more products could not be found in the store you chose")
                            .setTitle("Error Finding products");
                    final ListView input = new ListView(getContext());

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                            (getContext(), android.R.layout.simple_list_item_1, unavailable);

                    dataAdapter.setDropDownViewResource
                            (android.R.layout.simple_spinner_dropdown_item);

                    input.setAdapter(dataAdapter);

                    builder.setView(input);

                    builder.setPositiveButton("ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            } catch (JSONException e) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getContext(), "Shopping Cart is empty", Toast.LENGTH_LONG).show();
        }
    }

    private void regDetalle() {
        try {
            cant_error = new ArrayList<>();
            for (int i = 0; i < Config.idproducto.size(); i++) {

                JSONObject cantver = new JSONObject();
                cantver.put("codProducto", Config.idproducto.get(i));
                cantver.put("Cantidad", Config.cantidad.get(i));
                cantver.put("idSucursal", sucursal);

                if(Connection.getInstance().VerifCantidad(cantver)) {
                    JSONObject pad = new JSONObject();
                    pad.put("idProducto", Config.idproducto.get(i));
                    pad.put("idPedido", numpedido);
                    pad.put("Cantidad", Config.cantidad.get(i));
                    pad.put("idSucursal", sucursal);

                    Connection.getInstance().regDetalle(pad);
                }
                else{
                    cant_error.add(Config.idproducto.get(i));
                }
            }
            Log.e("cant_error",cant_error.toString());
            if(cant_error.isEmpty()) {
                Toast.makeText(getContext(), "Detalle Agregado Correctamente", Toast.LENGTH_LONG).show();
            }
            else{
                Connection.getInstance().deletePedido(numpedido);
                ArrayList<String> temp = new ArrayList<>();
                for(int i = 0 ; i < cant_error.size() ; i++){
                    JSONObject obj = Connection.getInstance().getProductobyId(cant_error.get(i));
                    temp.add(obj.getString("Nombre"));
                }
                final AlertDialog.Builder buildersuc = new AlertDialog.Builder(getContext());
                buildersuc.setMessage("Error, Cant. max of products not available")
                        .setTitle("Error");
                final ListView input = new ListView(getContext());

                ArrayAdapter<String> dataAdapter = null;
                try {
                    dataAdapter = new ArrayAdapter<String>
                            (getContext(), android.R.layout.simple_list_item_1, temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dataAdapter.setDropDownViewResource
                        (android.R.layout.simple_spinner_dropdown_item);

                input.setAdapter(dataAdapter);

                buildersuc.setView(input);

                buildersuc.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = buildersuc.create();
                dialog.show();
            }


        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al agregar detalle", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void regPedido() {
        try {
            JSONObject pad = new JSONObject();
            pad.put("sucursalRecojo", sucursal);
            pad.put("idCliente", Config.ClientLogged.get("Cedula"));
            pad.put("horaRecojo", fecha.getText());
            pad.put("Telefono", telefono.getText());
            if (encodedprescription == null) {
                pad.put("Imagen", "");
            } else {
                pad.put("Imagen", encodedprescription);
            }
            pad.put("Estado", 1);
            numpedido = Connection.getInstance().regPedido(pad);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean RequirePrescription() {
        for (int i = 0; i < Config.prescription.size(); i++) {
            if (Config.prescription.get(i).equalsIgnoreCase("Require prescription")) {
                return true;
            }
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            encodedprescription = Base64.encodeToString(byteArray, Base64.DEFAULT);
            if (encodedprescription != null) {
                submitresult();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_pedidos, container, false);
    }

    Integer[] imgid = {
    };

    private void getProducts() throws JSONException {
        dataProducts = Connection.getInstance().getProductosxSucursal(sucursal);
        allrelation = new ArrayList<Integer>();
        prescription = new ArrayList<String>();
        allProducts = new ArrayList<String>();
        allimg = new ArrayList<>();
        precios = new ArrayList<>();
        idproducto = new ArrayList<>();

        for (int x = 0; x < dataProducts.length(); x++) {
            JSONObject objeto = (JSONObject) dataProducts.get(x);
            allrelation.add(objeto.getInt("codProducto"));
            precios.add(objeto.getInt("Precio"));
        }
        for (int i = 0; i < allrelation.size(); i++) {
            JSONObject objeto = Connection.getInstance().getProductobyId(allrelation.get(i));
            allProducts.add(objeto.getString("Nombre"));
            byte[] decodedString = Base64.decode(objeto.getString("Image"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            allimg.add(decodedByte);
            idproducto.add(objeto.getInt("idProducto"));
            if (objeto.getInt("reqPrescripcion") == 1) {
                prescription.add("Require prescription");
            } else {
                prescription.add("");
            }
        }

        CustomList adapter = new
                CustomList((Activity) this.getContext(), allProducts, allimg, prescription, Config.ADD, precios);
        list = (ListView) getView().findViewById(R.id.list);
        list.setAdapter(adapter);

    }

    private void getPrescription() throws JSONException {
        dataPrescription = Connection.getInstance().getRecetas(MainActivity.clientInfo.getInt("Cedula"));
        Recetasbyid = new ArrayList<>();
        ArrayList<String> idReceta = new ArrayList<>();
        ArrayList<String> idDoctor = new ArrayList<>();
        ArrayList<Bitmap> presimg = new ArrayList<>();
        ArrayList<Integer> cedula = new ArrayList<>();


        for (int i = 0; i < dataPrescription.length(); i++) {
            JSONObject objeto = dataPrescription.getJSONObject(i);
            idReceta.add("Receta #" + objeto.getString("idReceta"));
            Recetasbyid.add(objeto.getInt("idReceta"));
            byte[] decodedString = Base64.decode(objeto.getString("Imagen"), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            presimg.add(decodedByte);
            idDoctor.add("ID Doctor: " + objeto.getString("idDoctor"));
            cedula.add(objeto.getInt("idCliente"));
        }

        CustomList adapter = new
                CustomList((Activity) this.getContext(), idReceta, presimg, idDoctor, Config.DELETE, cedula);
        prescriptions.setAdapter(adapter);

    }

    private List<String> listSucursales() throws JSONException {
        dataSucursales = Connection.getInstance().getSucursales();

        List<String> allNames = new ArrayList<String>();
        for (int i = 0; i < dataSucursales.length(); i++) {
            JSONObject objeto = (JSONObject) dataSucursales.get(i);
            allNames.add(objeto.getString("Nombre") + " - " + objeto.getString("detalleDireccion"));
        }
        return allNames;

    }

    private boolean IsProductAvaible() throws JSONException {
        dataPxS = Connection.getInstance().getProductosxSucursal(sucursal);
        unavailable = new ArrayList<String>();
        available = new ArrayList<Integer>();
        for (int i = 0; i < dataPxS.length(); i++) {
            JSONObject objeto = (JSONObject) dataPxS.get(i);
            available.add(objeto.getInt("codProducto"));

        }
        for (int i = 0; i < Config.idproducto.size(); i++) {
            if (!available.contains(Config.idproducto.get(i))) {
                unavailable.add(Config.allProducts.get(i));
            }
        }

        if (unavailable.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    private void cleanVariables() {
        encodedprescription = null;
        Config.allProducts.clear();
        Config.prescription.clear();
        Config.precios.clear();
        Config.allimg.clear();
        Config.idproducto.clear();
        Config.cantidad.clear();
        unavailable.clear();
        fecha.setText("");
        telefono.setText("");
    }


}
