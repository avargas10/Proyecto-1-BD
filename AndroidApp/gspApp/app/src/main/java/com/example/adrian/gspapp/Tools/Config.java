package com.example.adrian.gspapp.Tools;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esteban on 27/09/2017.
 */

public abstract class Config {
    public static String ip = "";
    public static List<String> allProducts = new ArrayList<String>();
    public static List<Bitmap> allimg = new ArrayList<>();
    public static List<Integer> precios = new ArrayList<>();
    public static List<String> prescription = new ArrayList<String>();
    public static List<Integer> idproducto = new ArrayList<Integer>();
    public static final int ADD = 0;
    public static final int DELETE = 1;
    public  static JSONObject ClientLogged;
    public static int PedidoReciente=-1;
    public static List<Integer> cantidad = new ArrayList<Integer>();

}
