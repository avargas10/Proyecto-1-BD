package com.example.adrian.gspapp.Tools;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adrian.gspapp.MainActivity;
import com.example.adrian.gspapp.Registro;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Adrian on 27/09/2017.
 */

public class Connection {
    private static Connection instance;

    private Connection() {

    }

    public static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    public boolean isConnectedToServer(String ip) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            URL myUrl = new URL(ip);
            HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
            connection.setConnectTimeout(15000);
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int clientLogin(String user, String pass) {
        if(isConnectedToServer("http://"+Config.ip+":58706")) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection conn;
            try {
                URL url = new URL("http://" + Config.ip + ":58706/api/Clientes?username=" + user + "&pass=" + pass);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(15000);
                conn.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                if (response.toString().equals("true")) {
                    getClientInfo(user);
                    conn.disconnect();
                    return 1;
                } else {
                    conn.disconnect();
                    return 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 2;
            }
            //new MyAsyncTask().execute(user,pass);
            //return true;
        }else{
            return 2;
        }

    }

    public int registroCliente(JSONObject data) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        if(isConnectedToServer("http://"+Config.ip+":58706")) {
            try {
                URL url = new URL("http://" + Config.ip + ":58706/api/Clientes");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                OutputStream os = conn.getOutputStream();
                os.write(data.toString().getBytes());
                os.flush();
                if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                    return 0;
                } else {
                    return 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 2;
            }
        }else{
            return 2;
        }

    }

    public JSONObject registroDireccion(JSONObject dataDir, Context context) throws JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        if(isConnectedToServer("http://"+Config.ip+":58706")) {
            try {
                URL url = new URL("http://" + Config.ip + ":58706/api/Direcciones");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                OutputStream os = conn.getOutputStream();
                os.write(dataDir.toString().getBytes());
                os.flush();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                JSONObject jData = new JSONObject(response.toString());
                return jData;
            } catch (Exception e) {
                e.printStackTrace();
                JSONObject jData = new JSONObject();
                jData.put("idDireccion", 1);
                return jData;
            }
        }else{
            Toast.makeText(context,"Error to connect with server!", Toast.LENGTH_LONG).show();
            JSONObject jData = new JSONObject();
            jData.put("idDireccion", 1);
            return jData;
        }
    }

    private void getClientInfo(String user){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;

            try {
                URL url = new URL("http://" + Config.ip + ":58706/api/Clientes?username=" + user);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                JSONObject client = new JSONObject(response.toString());
                MainActivity.clientInfo = client;
                System.out.println("CLIENTE: " + MainActivity.clientInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public JSONArray getDirections(int type, int id, Context context){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        if(isConnectedToServer("http://"+Config.ip+":58706")) {
            switch (type) {
                case 1:
                    try {
                        URL url = new URL("http://" + Config.ip + ":58706/api/Provincias/");
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        JSONArray jArray = new JSONArray(response.toString());
                        return jArray;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        URL url = new URL("http://" + Config.ip + ":58706/api/Cantones?Provincia=" + Integer.toString(id));
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        JSONArray jArray = new JSONArray(response.toString());

                        return jArray;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        URL url = new URL("http://" + Config.ip + ":58706/api/Distrito?canton=" + Integer.toString(id));
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        JSONArray jArray = new JSONArray(response.toString());

                        return jArray;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            JSONArray arr = new JSONArray();
            return arr;
        }else{
            Toast.makeText(context,"Error to connect with server!", Toast.LENGTH_LONG).show();
            JSONArray arr = new JSONArray();
            return arr;
        }
    }
    public JSONArray getProductos(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try{
            URL url = new URL("http://"+Config.ip + ":58706/api/Productos/" );
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONArray jArray=new JSONArray(response.toString());
            return jArray;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
