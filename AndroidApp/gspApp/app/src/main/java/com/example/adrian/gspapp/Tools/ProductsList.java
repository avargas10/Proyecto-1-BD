package com.example.adrian.gspapp.Tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adrian.gspapp.R;

import java.util.List;

/**
 * Created by Esteban on 06/10/2017.
 */

public class ProductsList extends ArrayAdapter<String> {
    private final Activity context;
    private final List web;
    private final List imageId;
    private final List precios;
    private final List prescription;
    private final int place;
    private final List cantidad;
    public ProductsList(Activity context, List web, List imageId, List prescription, int place, List precios, List cant) {
        super(context, R.layout.list_design, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.precios = precios;
        this.prescription = prescription;
        this.place = place;
        this.cantidad = cant;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        try {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_design, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.edit_txt2);
            TextView txtPrecios = (TextView) rowView.findViewById(R.id.edit_price2);
            TextView txtPres = (TextView) rowView.findViewById(R.id.edit_pres2);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.edit_img2);
            TextView txtcant = (TextView) rowView.findViewById(R.id.edit_idcant);


            txtTitle.setText((String) web.get(position));
            txtPrecios.setText(String.valueOf(precios.get(position)));
            txtPres.setText((String) prescription.get(position));
            txtcant.setText(String.valueOf(cantidad.get(position)));


            imageView.setImageBitmap((Bitmap) imageId.get(position));

            return rowView;
        }catch (Exception e){}
        return context.getLayoutInflater().inflate(R.layout.list_design, null, true);
    }
}
