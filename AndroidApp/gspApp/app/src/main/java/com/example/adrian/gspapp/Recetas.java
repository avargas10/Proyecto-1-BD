package com.example.adrian.gspapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.gspapp.Tools.Config;
import com.example.adrian.gspapp.Tools.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Interfaz.Swipe.SwipeMenu;
import Interfaz.Swipe.SwipeMenuCreator;
import Interfaz.Swipe.SwipeMenuItem;
import Interfaz.Swipe.SwipeMenuListView;

public class Recetas extends Fragment {

    public static JSONArray listaPost;
    FloatingActionButton fab;
    public static SwipeMenuListView lista;
    SwipeMenuCreator creator;
    public static  AppAdapter mAdapter;



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Prescriptions");

        try {
            listaPost= Connection.getInstance().getRecetas(MainActivity.clientInfo.getInt("Cedula"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        lista = getView().findViewById(R.id.listView);
        fab= getView().findViewById(R.id.fabRecetas);
        mAdapter=new AppAdapter(this.getContext(), listaPost);
        lista.setAdapter(mAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config.recetasFlag=true;
                Intent intent = new Intent(getContext(), nuevaReceta.class);
                startActivity(intent);
            }
        });
        creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem share = new SwipeMenuItem(
                        getContext());
                // set item background
                share.setBackground(new ColorDrawable(Color.parseColor("#1aa3ff")));
                // set item width
                share.setWidth(dp2px(90));
                // set item title
                share.setTitle("EDIT");
                // set item title fontsize
                share.setTitleSize(18);
                // set item title font color
                share.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(share);


                SwipeMenuItem delete = new SwipeMenuItem(
                        getContext());
                // set item background
                delete.setBackground(new ColorDrawable(Color.parseColor("#e15258")));
                // set item width
                delete.setWidth(dp2px(90));
                // set item title                        adapter.notifyDataSetChanged();

                delete.setTitle("DELETE");
                // set item title fontsize
                delete.setTitleSize(18);
                // set item title font color
                delete.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(delete);
            }
        };
        // set creator
        lista.setMenuCreator(creator);
        lista.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        mAdapter.share(position);

                        break;
                    case 1:
                        mAdapter.delete(position);
                        lista.setAdapter(mAdapter);

                        break;
                }
                return false;
            }
        });

        lista.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        lista.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_recetas, container, false);
    }


    class AppAdapter extends ArrayAdapter<JSONObject>{
        Context context;
        JSONArray jArray;

        public  AppAdapter(Context context, JSONArray li){
            super(context, R.layout.post_principal);
            this.context=context;
            this.jArray=li;
        }
        @Override
        public int getCount() {
            return this.jArray.length();
        }

        @Override
        public JSONObject getItem(int position) {
            try {
                return (JSONObject) this.jArray.get(position);
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
                convertView = View.inflate(this.context,
                        R.layout.post_principal, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            try {
                JSONObject obj=this.jArray.getJSONObject(position);
                holder.tv_name.setText("Id Prescription: "+obj.getInt("idReceta"));
                holder.tv_descrip.setText("Id Doctor: "+obj.getInt("idDoctor"));
                byte[] decodedString = Base64.decode(obj.getString("Imagen"), Base64.DEFAULT);
                Bitmap image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.imagen.setImageBitmap(image);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return convertView;
        }

        public void delete(final int pos){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Delete this prescription?")
                    .setTitle("Delete Prescription");
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            try {
                                if(Connection.getInstance().deleteReceta(jArray.getJSONObject(pos).getInt("idReceta"))){
                                    jArray.remove(pos);
                                    mAdapter.notifyDataSetChanged();
                                }else{
                                    Toast.makeText(context, "Error Deleting the Prescription!", Toast.LENGTH_LONG);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            builder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        }

        public void share(int pos){
            try {
                Config.recetaparEditar=this.jArray.getJSONObject(pos);
                Config.recetasFlag=false;
                Config.medicamentoporReceta=Connection.getInstance().getProductos(this.jArray.getJSONObject(pos).getInt("idReceta"));
                Intent intent = new Intent(getContext(), nuevaReceta.class);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        class ViewHolder {
            TextView tv_name;
            TextView tv_descrip;
            ImageView imagen;
            public ViewHolder(View view) {
                tv_name = view.findViewById(R.id.titulo_postPrincipal);
                tv_descrip=view.findViewById(R.id.descripcion_postPrincipal);
                imagen=view.findViewById(R.id.imgRecetasAdapter);
                view.setTag(this);
            }
        }



    }





}
