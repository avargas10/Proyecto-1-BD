package com.example.adrian.gspapp.Tools;

/**
 * Created by Esteban on 30/09/2017.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adrian.gspapp.R;

import java.util.List;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final List web;
    private final List imageId;
    private final List precios;
    private final List prescription;
    private final int place;
    public CustomList(Activity context, List web, List imageId, List prescription, int place, List precios) {
        super(context, R.layout.list_design, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.precios = precios;
        this.prescription = prescription;
        this.place = place;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        try {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_design, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt2);
            TextView txtPrecios = (TextView) rowView.findViewById(R.id.price2);
            TextView txtPres = (TextView) rowView.findViewById(R.id.pres2);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.img2);
            ImageView img = (ImageView) rowView.findViewById(R.id.imageView1);


            txtTitle.setText((String) web.get(position));
            txtPrecios.setText(String.valueOf(precios.get(position)));
            txtPres.setText((String) prescription.get(position));

            imageView.setImageBitmap((Bitmap) imageId.get(position));

            if(place == 0){
                img.setImageResource(R.drawable.add);
            }
            else if(place == 1){
                img.setImageResource(R.drawable.delete);
            }

            return rowView;
        }catch (Exception e){}
        return context.getLayoutInflater().inflate(R.layout.list_design, null, true);
    }
}