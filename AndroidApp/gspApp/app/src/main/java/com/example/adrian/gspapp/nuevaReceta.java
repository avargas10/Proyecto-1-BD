package com.example.adrian.gspapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        getSupportActionBar().setTitle("Registrar Receta");
        list=(ListView) findViewById(R.id.listregReceta);
        imagen=(ImageView) findViewById(R.id.imageViewReceta);
        send=(Button) findViewById(R.id.btnSendRecetas);
        selectedItems=new ArrayList();
        try {
           // getProducts();
            initItems();
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
                Toast.makeText(getApplicationContext(), selectedItems.toString(),Toast.LENGTH_LONG).show();
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
            Bitmap image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);;
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
            viewHolder.checkBox.setChecked(list.get(position).checked);

            final String itemStr = list.get(position).ItemString;
            final String itemStr1 = list.get(position).ItemString2;
            viewHolder.text.setText(itemStr);
            viewHolder.text1.setText("Codigo de Producto: "+itemStr1);
            viewHolder.checkBox.setTag(position);

            /*
            viewHolder.checkBox.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    list.get(position).checked = b;

                    Toast.makeText(getApplicationContext(),
                            itemStr + "onCheckedChanged\nchecked: " + b,
                            Toast.LENGTH_LONG).show();
                }
            });
            */

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean newState = !list.get(position).isChecked();
                    list.get(position).checked = newState;
                    Toast.makeText(getApplicationContext(),
                            itemStr + "setOnClickListener\nchecked: " + newState,
                            Toast.LENGTH_LONG).show();
                    selectedItems.add(Integer.parseInt(itemStr1));
                }
            });

            try {
                viewHolder.checkBox.setChecked(isChecked(position));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return rowView;
        }
            /*if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.list_nueva_receta, null);
                new AppAdapter.ViewHolder(convertView);
            }
            AppAdapter.ViewHolder holder = (AppAdapter.ViewHolder) convertView.getTag();

                holder.tv_name.setText(items.get(position).ItemString);
                holder.tv_descrip.setText("Codigo de Producto: "+items.get(position).ItemString2);
                holder.imagen.setImageBitmap(items.get(position).ItemDrawable);
               holder.check.setChecked(items.get(position).checked);

            ViewHolder

            return convertView;
        }
*/


       /* class ViewHolder {
            public TextView tv_name;
           public TextView tv_descrip;
            public CheckBox check;
            public ImageView imagen;

            public ViewHolder(View view) {
                tv_name = view.findViewById(R.id.txtProductoRecetas);
                tv_descrip = view.findViewById(R.id.txtCodigoProductoRecetas);
                imagen = view.findViewById(R.id.imgRecetas);
                check=view.findViewById(R.id.checkBox);
                view.setTag(this);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }



        }*/
        class ViewHolder {
           CheckBox checkBox;
           ImageView icon;
           TextView text, text1;
       }
    }
}
