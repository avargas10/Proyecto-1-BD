package com.example.adrian.gspapp.Tools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.gspapp.EditProducts;
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
    public View getView(final int position, final View view, ViewGroup parent) {
        try {
            LayoutInflater inflater = context.getLayoutInflater();
            final View rowView = inflater.inflate(R.layout.edit_product, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.edit_txt2);
            //TextView txtPrecios = (TextView) rowView.findViewById(R.id.edit_price2);
            TextView txtPres = (TextView) rowView.findViewById(R.id.edit_pres2);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.edit_img2);
            final TextView txtcant = (TextView) rowView.findViewById(R.id.edit_idcant);

            final Spinner spinner = (Spinner) rowView.findViewById(R.id.spinner_cant);
// Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.cantidad_array, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            int i = adapter.getPosition(cantidad.get(position).toString());
            spinner.setSelection(i);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View view, int pos, long id) {
                    Log.e("cantidades inicial",Config.selectedcant.toString());
                    try{
                        String read = parent.getItemAtPosition(pos).toString();
                        Config.selectedcant.set(position,Integer.parseInt(read));
                        Log.e("cantidades final c",Config.selectedcant.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("cantidades final i",Config.selectedcant.toString());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView parent) {
                    // Do nothing.
                }
            });

            txtTitle.setText((String) web.get(position));
            //txtPrecios.setText(String.valueOf(precios.get(position)));
            txtPres.setText((String) prescription.get(position));
            //txtcant.setText(String.valueOf(cantidad.get(position)));


            imageView.setImageBitmap((Bitmap) imageId.get(position));


            return rowView;
        }catch (Exception e){}
        return context.getLayoutInflater().inflate(R.layout.edit_product, null, true);
    }
}
