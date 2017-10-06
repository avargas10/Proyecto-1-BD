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
import android.widget.NumberPicker;
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
            if(place==0) {
                NumberPicker np = (NumberPicker) rowView.findViewById(R.id.np);

                //Populate NumberPicker values from minimum and maximum value range
                //Set the minimum value of NumberPicker
                np.setMinValue(0);
                //Specify the maximum value/number of NumberPicker
                np.setMaxValue(10);

                //Gets whether the selector wheel wraps when reaching the min/max value.
                np.setWrapSelectorWheel(true);

            }
            else if (place==1){
                NumberPicker np = (NumberPicker) rowView.findViewById(R.id.np);

                //Populate NumberPicker values from minimum and maximum value range
                //Set the minimum value of NumberPicker
                np.setMinValue(0);
                //Specify the maximum value/number of NumberPicker
                np.setMaxValue(10);

                //Gets whether the selector wheel wraps when reaching the min/max value.
                np.setWrapSelectorWheel(true);
                np.setValue(Config.cantidad.get(position));

            }


            txtTitle.setText((String) web.get(position));
            txtPrecios.setText(String.valueOf(precios.get(position)));
            txtPres.setText((String) prescription.get(position));


            imageView.setImageBitmap((Bitmap) imageId.get(position));

            return rowView;
        }catch (Exception e){}
        return context.getLayoutInflater().inflate(R.layout.list_design, null, true);
    }
}