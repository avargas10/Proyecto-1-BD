package com.example.adrian.gspapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.gspapp.Tools.Config;
import com.example.adrian.gspapp.Tools.Connection;
import com.example.adrian.gspapp.Tools.CustomList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class nuevaReceta extends AppCompatActivity {
    ListView list;
    ImageView imagen;
    private static final int CAMERA_REQUEST = 1888;
    String encodedprescription=null;
    JSONArray lista;
    static List<String> allProducts;
    static List<Bitmap> allimg;
    static List<Integer> idproducto;
    static List<Integer> selectedItems;
    private AppAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_receta);
        getSupportActionBar().setTitle("Registrar Receta");
        list=(ListView) findViewById(R.id.listregReceta);
        imagen=(ImageView) findViewById(R.id.imageViewReceta);
        try {
            getProducts();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter=new AppAdapter();
        list.setAdapter(mAdapter);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);

                }

            }
        });

        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // (ImageView)view.findViewById(R.id.imgCheck).(R.drawable.fillCheck);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap resized = Bitmap.createScaledBitmap(photo, 100, 100, true);
            imagen.setImageBitmap(resized);
            resized.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encodedprescription = Base64.encodeToString(byteArray, Base64.DEFAULT);
            if(encodedprescription != null) {
               // submitresult();

            }
        }
    }
     private void getProducts() throws JSONException {
         lista=Connection.getInstance().getMedicamentos();
         System.out.println("AQUI ESTA LA LISTA: "+lista.length()+"  "+lista.toString());
         allProducts = new ArrayList<String>();
         allimg = new ArrayList<>();
         idproducto = new ArrayList<>();
         for(int i=0; i<lista.length(); i++){
             JSONObject obj= (JSONObject) lista.get(i);
             allProducts.add((String) obj.get("Nombre"));
             byte[] decodedString = Base64.decode(obj.getString("Image"), Base64.DEFAULT);
             Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
             allimg.add(decodedByte);
             idproducto.add(obj.getInt("idProducto"));
         }
     }

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lista.length();
        }

        @Override
        public JSONObject getItem(int position) {
            try {
                return (JSONObject) lista.get(position);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            // menu type count
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            // current menu type
            return position % 3;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.list_nueva_receta, null);
                new AppAdapter.ViewHolder(convertView);
            }
            AppAdapter.ViewHolder holder = (AppAdapter.ViewHolder) convertView.getTag();

                holder.tv_name.setText(allProducts.get(position));
                holder.tv_descrip.setText("Codigo de Producto: "+Integer.toString(idproducto.get(position)));
                holder.imagen.setImageBitmap(allimg.get(position));



            return convertView;
        }


        class ViewHolder {
            public TextView tv_name;
           public TextView tv_descrip;
            public ImageView imagen, imageCheck;

            public ViewHolder(View view) {
                tv_name = view.findViewById(R.id.txtProductoRecetas);
                tv_descrip = view.findViewById(R.id.txtCodigoProductoRecetas);
                imagen = view.findViewById(R.id.imgRecetas);
                imageCheck=view.findViewById(R.id.imgCheck);
                view.setTag(this);
            }
        }
    }
}
