package com.example.adrian.gspapp;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Interfaz.PostAdapter;
import Interfaz.Swipe.SwipeMenu;
import Interfaz.Swipe.SwipeMenuCreator;
import Interfaz.Swipe.SwipeMenuItem;
import Interfaz.Swipe.SwipeMenuListView;

public class Recetas extends Fragment {

    JSONArray listaPost;
    JSONObject ob1, obj2, obj3, obj4, obj5, obj6, obj7, obj8 ,obj9, obj10;

    private SwipeMenuListView lista;
    PostAdapter adapter;
    SwipeMenuCreator creator;
    private  AppAdapter mAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Recetas");

        try {
            datos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        lista = getView().findViewById(R.id.listView);
        mAdapter=new AppAdapter();
        lista.setAdapter(mAdapter);
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

    private JSONArray datos() throws JSONException {
        ob1 = new JSONObject();
        obj2 = new JSONObject();
        obj3 = new JSONObject();
        obj4 = new JSONObject();
        obj5 = new JSONObject();
        obj6 = new JSONObject();
        obj7 = new JSONObject();
        obj8 = new JSONObject();
        obj9 = new JSONObject();
        obj10= new JSONObject();
        listaPost = new JSONArray();

        ob1.put("titulo", "documentacion");
        ob1.put("indicador", "Realizado");
        ob1.put("contenido", "docx ");

        obj2.put("titulo", "foto1");
        obj2.put("indicador", "Pendiente");
        obj2.put("contenido", "jpg  ");

        obj3.put("titulo", "resumenes");
        obj3.put("indicador", "Pendiente");
        obj3.put("contenido", "pdf ");

        obj4.put("titulo", "back in black");
        obj4.put("indicador", "Realizado");
        obj4.put("contenido", "mp3 ");

        obj5.put("titulo", "img11");
        obj5.put("indicador", "Pendiente");
        obj5.put("contenido", "jpg");

        obj6.put("titulo", "algorithms");
        obj6.put("indicador", "Realizado");
        obj6.put("contenido", "pdf");

        obj7.put("titulo", "portada");
        obj7.put("indicador", "Realizado");
        obj7.put("contenido", "png");

        obj8.put("titulo", "presentacion_comu");
        obj8.put("indicador", "Pendiente");
        obj8.put("contenido", "pptx");

        obj9.put("titulo", "bibliotecas");
        obj9.put("indicador", "Pendiente");
        obj9.put("contenido", "zip");

        obj10.put("titulo", "PRUEBA");
        obj10.put("indicador", "Pendiente");
        obj10.put("contenido", "PRUEBA");


        listaPost.put(ob1);
        listaPost.put(obj2);
        listaPost.put(obj3);
        listaPost.put(obj4);
        listaPost.put(obj5);
        listaPost.put(obj6);
        listaPost.put(obj7);
        listaPost.put(obj8);
        listaPost.put(obj9);
        listaPost.put(obj10);

        return listaPost;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_recetas, container, false);
    }

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listaPost.length();
        }

        @Override
        public JSONObject getItem(int position) {
            try {
                return (JSONObject) listaPost.get(position);
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
                convertView = View.inflate(getContext(),
                        R.layout.post_principal, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            try {
                JSONObject obj=listaPost.getJSONObject(position);
                holder.tv_name.setText(obj.getString("titulo"));
                holder.tv_descrip.setText(obj.getString("contenido"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return convertView;
        }
        public void delete(int pos){
            try {
                Toast.makeText(getContext(), "Se ha eliminado el archivo "+ listaPost.getJSONObject(pos).getString("titulo")+"."+
                                listaPost.getJSONObject(pos).getString("contenido"),
                        Toast.LENGTH_LONG).show();
                listaPost.remove(pos);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        class ViewHolder {
            TextView tv_name;
            TextView tv_descrip;
            public ViewHolder(View view) {
                tv_name = view.findViewById(R.id.titulo_postPrincipal);
                tv_descrip=view.findViewById(R.id.descripcion_postPrincipal);
                view.setTag(this);
            }
        }


    }



}
