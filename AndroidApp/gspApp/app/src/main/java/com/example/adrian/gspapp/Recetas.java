package com.example.adrian.gspapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.adrian.gspapp.Tools.Config;
import com.example.adrian.gspapp.Tools.CustomList;
import com.example.adrian.gspapp.Tools.RecetasList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recetas extends Fragment {
    private static  final  String TAG="Recetas";
    JSONObject ob1, obj2, obj3, obj4, obj5, obj6, obj7, obj8 ,obj9;
    JSONArray listaPost;
    static List<JSONObject> data= new ArrayList<JSONObject>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Recetas");
        SwipeMenuListView listView=getView().findViewById(R.id.listView);

        RecetasList adapter = null;
        try {
            System.out.println("AQUI ESTOY!!!!");
            adapter = new
                    RecetasList((Activity) this.getContext(), datos());
        } catch (JSONException e) {
            System.out.println("ECEPCION!!!!!!!!!!");
            e.printStackTrace();
        }
        // list=(ListView)getView().findViewById(R.id.list);
        listView.setAdapter(adapter);
       /* try {
            adapter = new PostAdapter(getActivity(), datos());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x1a, 0xa3,
                        0xff)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(getContext(),"uno",Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getContext(),"Delete",Toast.LENGTH_LONG).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_recetas, container, false);
    }

    private List datos() throws JSONException {

        ob1 = new JSONObject();
        obj2 = new JSONObject();
        obj3 = new JSONObject();
        obj4 = new JSONObject();
        obj5 = new JSONObject();
        obj6 = new JSONObject();
        obj7 = new JSONObject();
        obj8 = new JSONObject();
        obj9 = new JSONObject();
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




        data.add(ob1);
        data.add(obj2);
        data.add(obj3);
        data.add(obj4);
        data.add(obj5);
        data.add(obj6);
        data.add(obj7);
        data.add(obj8);
        data.add(obj9);

        return data;
    }


}
