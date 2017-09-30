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
    public CustomList(Activity context,
                      List web, List imageId) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText((String) web.get(position));

        imageView.setImageBitmap((Bitmap)imageId.get(position));
        return rowView;
    }
}