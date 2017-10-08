package com.example.adrian.gspapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.gspapp.Tools.Config;
import com.example.adrian.gspapp.Tools.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class nuevaReceta extends AppCompatActivity {
    ListView list;
    ImageView imagen;
    EditText doctor;
    private static final int CAMERA_REQUEST = 1888;
    String encodedprescription=null;
    JSONArray lista;
    static List<Integer> selectedItems;
    private AppAdapter mAdapter;
    List<Item> items;
    Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_receta);
        getSupportActionBar().setTitle("Prescription Settings");
        list=(ListView) findViewById(R.id.listregReceta);
        imagen=(ImageView) findViewById(R.id.imageViewReceta);
        send=(Button) findViewById(R.id.btnSendRecetas);
        doctor=(EditText) findViewById(R.id.txtidDoctor);
        selectedItems=new ArrayList();
        try {
           // getProducts();
            initItems();
            if(!Config.recetasFlag){
                JSONArray lisMed=Connection.getInstance().getProductos(Config.recetaparEditar.getInt("idReceta"));
                for(int x=0;x<lisMed.length();x++){
                    selectedItems.add(lisMed.getJSONObject(x).getInt("idMedicamento"));
                }
                encodedprescription=Config.recetaparEditar.getString("Imagen");
                doctor.setText(Config.recetaparEditar.getString("idDoctor"));
                byte[] decodedString = Base64.decode(encodedprescription, Base64.DEFAULT);
                Bitmap image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imagen.setImageBitmap(image);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter=new AppAdapter(this, items);
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

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // final ImageView image=view.findViewById(R.id.imgCheck);
                //image.setImageResource(R.drawable.fillcheck);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject obj= new JSONObject();
                try {
                    obj.put("idCliente", MainActivity.clientInfo.getInt("Cedula"));
                    obj.put("Imagen", encodedprescription);
                    obj.put("Estado", 1);
                    obj.put("idDoctor", Integer.parseInt(String.valueOf(doctor.getText())));
                    if(Config.recetasFlag){
                        if(Connection.getInstance().registroRecetas(obj, selectedItems)){
                            Toast.makeText(getApplicationContext(), "Prescription Created!", Toast.LENGTH_SHORT).show();
                            doctor.setText("");
                            imagen.setImageResource(R.drawable.cameras1);
                            for(int i=0; i<mAdapter.list.size();i++){
                                mAdapter.list.get(i).checked=false;
                                mAdapter.notifyDataSetChanged();
                            }
                            obj.put("idReceta", Config.recetaRegistrada);
                            Recetas.mAdapter.jArray.put(obj);
                            Recetas.mAdapter.notifyDataSetChanged();
                        }else{
                           // Toast.makeText(getApplicationContext(), "Error Creating Prescription!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Prescription Created!", Toast.LENGTH_SHORT).show();
                            doctor.setText("");
                            imagen.setImageResource(R.drawable.cameras1);
                            for(int i=0; i<mAdapter.list.size();i++){
                                mAdapter.list.get(i).checked=false;
                                mAdapter.notifyDataSetChanged();

                            }
                            obj.put("idReceta", Config.recetaRegistrada);
                            Recetas.mAdapter.jArray.put(obj);
                            Recetas.mAdapter.notifyDataSetChanged();
                        }
                    }else{
                        obj.put("idReceta", Config.recetaparEditar.getInt("idReceta"));
                        System.out.println("AQUI ESTA LA LISTA: "+selectedItems.toString());
                        if(Connection.getInstance().updateRecetas(obj, selectedItems)){
                            for(int a=0; a<Recetas.mAdapter.jArray.length();a++){
                                if(Recetas.mAdapter.jArray.getJSONObject(a).getInt("idReceta")==Config.recetaparEditar.getInt("idReceta")) {
                                    Recetas.mAdapter.jArray.remove(a);
                                    Recetas.mAdapter.jArray.put(obj);
                                    mAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }else{
                            for(int a=0; a<Recetas.mAdapter.jArray.length();a++){
                                if(Recetas.mAdapter.jArray.getJSONObject(a).getInt("idReceta")==Config.recetaparEditar.getInt("idReceta")) {
                                    Recetas.mAdapter.jArray.remove(a);
                                    Recetas.mAdapter.jArray.put(obj);
                                    mAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private void initItems() throws JSONException {
        lista=Connection.getInstance().getMedicamentos();
        items = new ArrayList<Item>();


        for(int i=0; i<lista.length(); i++){
            JSONObject obj= (JSONObject) lista.get(i);
            String titulo= (String) obj.get("Nombre");
            byte[] decodedString = Base64.decode(obj.getString("Image"), Base64.DEFAULT);
            Bitmap image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            String s =  Integer.toString((Integer) obj.get("idProducto"));
            boolean b = false;
            Item item = new Item(image, titulo, s,b);
            items.add(item);
        }

    }

    public class Item {
        boolean checked;
        Bitmap ItemDrawable;
        String ItemString, ItemString2;

        Item(Bitmap drawable, String t,String d, boolean b){
            ItemDrawable = drawable;
            ItemString = t;
            ItemString2=d;
            checked = b;
        }

        public boolean isChecked(){
            return checked;
        }
    }

    class AppAdapter extends BaseAdapter {
        private Context context;
        private List<Item> list;

        AppAdapter(Context c, List<Item> l) {
            context = c;
            list = l;
        }

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
       public boolean isChecked(int position) throws JSONException {
            return list.get(position).checked;
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            // reuse views
            ViewHolder viewHolder = new ViewHolder();
            if (rowView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_nueva_receta, null);

                viewHolder.checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);
                viewHolder.icon = (ImageView) rowView.findViewById(R.id.imgRecetas);
                viewHolder.text = (TextView) rowView.findViewById(R.id.txtProductoRecetas);
                viewHolder.text1 = (TextView) rowView.findViewById(R.id.txtCodigoProductoRecetas);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }

            viewHolder.icon.setImageBitmap(list.get(position).ItemDrawable);
            final String itemStr = list.get(position).ItemString;
            final String itemStr1 = list.get(position).ItemString2;
            viewHolder.text.setText(itemStr);
            viewHolder.text1.setText("Product Id: "+itemStr1);
            if(!Config.recetasFlag){
                for(int z=0;z<selectedItems.size();z++){
                    if(selectedItems.get(z)==Integer.parseInt(itemStr1)){
                        viewHolder.checkBox.setChecked(list.get(position).checked=true);
                        break;
                    }
                }
            }else {
                viewHolder.checkBox.setChecked(list.get(position).checked);
            }
            viewHolder.checkBox.setTag(position);

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean newState = !list.get(position).isChecked();
                    list.get(position).checked = newState;
                    if(newState){
                        selectedItems.add(Integer.parseInt(itemStr1));
                    }else{
                        for(int i=0; i<selectedItems.size();i++ ){
                            if(selectedItems.get(i)==Integer.parseInt(itemStr1)){
                                selectedItems.remove(i);
                                break;
                            }
                        }
                    }
                    Toast.makeText(getApplicationContext(),
                            itemStr + "setOnClickListener\nchecked: " + newState,
                            Toast.LENGTH_LONG).show();
                }
            });

            try {
                viewHolder.checkBox.setChecked(isChecked(position));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return rowView;
        }


        class ViewHolder {
           CheckBox checkBox;
           ImageView icon;
           TextView text, text1;
       }
    }
}
