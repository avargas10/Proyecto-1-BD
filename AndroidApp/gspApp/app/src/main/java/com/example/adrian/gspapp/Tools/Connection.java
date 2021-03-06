package com.example.adrian.gspapp.Tools;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adrian.gspapp.MainActivity;
import com.example.adrian.gspapp.navigationDrawer;

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
    public Context context;

    private Connection() {

    }

    public static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    public boolean isConnectedToServer(String ip) {
        System.out.println(ip);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try{
            URL myUrl = new URL("http://"+ip+":58706");
            HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
            connection.setConnectTimeout(10000);
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clientLogin(String user, String pass) {
        if(!isConnectedToServer(Config.ip)){
            Toast.makeText(context,"Error to connect with server!", Toast.LENGTH_SHORT).show();

            return false;
        }else {
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
                    return true;
                } else {
                    conn.disconnect();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            //new MyAsyncTask().execute(user,pass);
            //return true;
        }

    }

    public int registroCliente(JSONObject data) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(!isConnectedToServer(Config.ip)){
            openLogin();

            return 10;
        }else {
            HttpURLConnection conn;
            try {
                URL url = new URL("http://" + Config.ip + ":58706/api/Clientes");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                OutputStream os = conn.getOutputStream();
                os.write(data.toString().getBytes());
                os.flush();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                    return 2;
                } else if(conn.getResponseCode()==HttpURLConnection.HTTP_CONFLICT) {
                    return 0;
                }else if(conn.getResponseCode()==HttpURLConnection.HTTP_BAD_REQUEST){
                    return 1;
                }
                else if(conn.getResponseCode()==HttpURLConnection.HTTP_BAD_GATEWAY){
                    return 3;
                }else{
                    return 10;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 10;
            }
        }
    }

    public boolean updateCliente(JSONObject data) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(!isConnectedToServer(Config.ip)){
            openLogin();

            return false;
        }else {
            HttpURLConnection conn;
            try {
                URL url = new URL("http://" + Config.ip + ":58706/api/Clientes");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setRequestMethod("PUT");
                OutputStream os = conn.getOutputStream();
                os.write(data.toString().getBytes());
                os.flush();
                if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean registroRecetas(JSONObject data, List med){
        HttpURLConnection conn;
        URL url;
        OutputStream os;
        BufferedReader in;
        String inputLine;
        StringBuffer response;
        try {
             url = new URL("http://" + Config.ip + ":58706/api/Recetas");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
             os = conn.getOutputStream();
            os.write(data.toString().getBytes());
            os.flush();
             in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
             response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONObject jData = new JSONObject(response.toString());
            Config.recetaRegistrada=jData.getInt("idReceta");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                conn.disconnect();
                for(int i=0; i<med.size();i++) {
                    jData.put("idMedicamento",med.get(i));
                    url = new URL("http://" + Config.ip + ":58706/api/DetalleReceta");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("POST");
                    os = conn.getOutputStream();
                    os.write(jData.toString().getBytes());
                    os.flush();
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                        conn.disconnect();
                    } else {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRecetas(JSONObject data, List med){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;

        try {
            URL url = new URL("http://" + Config.ip + ":58706/api/Recetas");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            OutputStream os = conn.getOutputStream();
            os.write(data.toString().getBytes());
            os.flush();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                conn.disconnect();
                return deleteDetalleReceta(data.getInt("idReceta"), med);
            } else {
                conn.disconnect();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDetalleReceta(int id, List med){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://" + Config.ip + ":58706/api/DetalleReceta?idReceta="+id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            conn.disconnect();
            return updateDetalleReceta(id, med);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void deletePedido(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://" + Config.ip + ":58706/api/Pedidos?id="+id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("DELETE");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            conn.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean deleteReceta(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://" + Config.ip + ":58706/api/Recetas/"+id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("DELETE");
            conn.connect();
            if(conn.getResponseCode()==HttpURLConnection.HTTP_CREATED){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDetalleReceta(int id, List med){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            HttpURLConnection conn;
            JSONObject jData = new JSONObject();
            jData.put("idReceta", id);
            for (int i = 0; i < med.size(); i++) {
                jData.put("idMedicamento", med.get(i));
                URL url = new URL("http://" + Config.ip + ":58706/api/DetalleReceta");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("POST");
                OutputStream os = conn.getOutputStream();
                os.write(jData.toString().getBytes());
                os.flush();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                    conn.disconnect();
                    //return true;
                } else {
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public JSONArray getProductos(int idReceta){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONArray jArray =new JSONArray();
        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/DetalleReceta/"+Integer.toString(idReceta) );
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            jArray=new JSONArray(response.toString());
            return jArray;
        } catch (Exception e) {
            e.printStackTrace();
            return jArray;
        }
    }

    public JSONArray getRecetas(int cedula){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        JSONArray jArray =new JSONArray();
        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Recetas/"+Integer.toString(cedula) );
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
             jArray=new JSONArray(response.toString());
            return jArray;
        } catch (Exception e) {
            e.printStackTrace();
            return jArray;
        }
    }

    public boolean sendEmail(String username){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(!isConnectedToServer(Config.ip)){
            openLogin();

            return false;
        }else {
            HttpURLConnection conn;
            try {
                URL url = new URL("http://" + Config.ip + ":58706/api/Clientes?username="+username);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                if (response.toString().equals("true")) {
                    conn.disconnect();
                    return true;
                } else {
                    conn.disconnect();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    public boolean ActivationEmail(int cedula){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(!isConnectedToServer(Config.ip)){
            openLogin();
            return false;
        }else {
            HttpURLConnection conn;
            try {
                URL url = new URL("http://" + Config.ip + ":58706/api/Clientes?cedula="+cedula);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                if (response.toString().equals("true")) {
                    conn.disconnect();
                    return true;
                } else {
                    conn.disconnect();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

    }

    public  boolean deleteClient(JSONObject data){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(!isConnectedToServer(Config.ip)){
            openLogin();

            return false;
        }else {
            HttpURLConnection conn;
            try {
                URL url = new URL("http://" + Config.ip + ":58706/api/Clientes");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setRequestMethod("DELETE");
                OutputStream os = conn.getOutputStream();
                os.write(data.toString().getBytes());
                os.flush();
                if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public JSONArray getMedicamentos(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try{
            URL url = new URL("http://"+Config.ip + ":58706/api/Productos/1" );
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

    public boolean deleteDetalle(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        try {
            URL url = new URL("http://" + Config.ip + ":58706/api/DetallePedido?idPedido="+id);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public JSONObject UpdateDetallePedido(JSONObject dataPed){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/DetallePedido");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("PUT");
            OutputStream os = conn.getOutputStream();
            os.write(dataPed.toString().getBytes());
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
            return null;
        }
    }

    public JSONObject UpdatePedido(JSONObject dataPed){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Pedidos");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("PUT");
            OutputStream os = conn.getOutputStream();
            os.write(dataPed.toString().getBytes());
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
            return null;
        }
    }

    public JSONObject UpdatePad(JSONObject dataPed){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Padecimientos");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("PUT");
            OutputStream os = conn.getOutputStream();
            os.write(dataPed.toString().getBytes());
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
            return null;
        }
    }

    public JSONObject registroDireccion(JSONObject dataDir) throws JSONException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Direcciones");
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
            jData.put("idDireccion",1);
            return jData;
        }
    }

    public JSONObject updateDireccion(JSONObject dataDir) throws  JSONException{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Direcciones");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("PUT");
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
            jData.put("idDireccion",1);
            return jData;
        }
    }

    private void getClientInfo(String user){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Clientes?username=" + user );
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            conn.disconnect();
            JSONObject client =new JSONObject(response.toString());
            MainActivity.clientInfo=client;
            URL url1 = new URL("http://"+Config.ip + ":58706/api/Direcciones/" + Integer.toString(MainActivity.clientInfo.getInt("Direccion")) );
            conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in1 = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine1;
            StringBuffer response1 = new StringBuffer();
            while ((inputLine1 = in1.readLine()) != null) {
                response1.append(inputLine1);
            }
            conn.disconnect();
            JSONObject client1 =new JSONObject(response1.toString());
            MainActivity.clientDir=client1;
            Config.ClientLogged = client;
            System.out.println("CLIENTE: "+MainActivity.clientInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONArray getPedidobyId(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Pedidos?id=" + id );
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getEstadobyId(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Estados?id=" + id );
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONObject jArray=new JSONObject(response.toString());
            return jArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getPadbyId(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Padecimientos?idPad=" + id );
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONObject jArray=new JSONObject(response.toString());
            return jArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getPedidobyIdPedido(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Pedidos?idPedido=" + id );
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONObject jArray=new JSONObject(response.toString());
            return jArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getDetallebyId(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/DetallePedido/" + id );
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean VerifCantidad(JSONObject pedido){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Productos");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("PUT");
            OutputStream os = conn.getOutputStream();
            os.write(pedido.toString().getBytes());
            os.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);

            }
            conn.disconnect();
            Log.e("recibido",response.toString());
            Log.e("sended",pedido.toString());
            if(response.toString().contains("true")) {
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public JSONArray getSucursalbyId(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Sucursal?id=" + id );
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONArray jo=new JSONArray(response.toString());
            return jo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getProductobyId(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Productos?Cod=" + id );
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONObject jo=new JSONObject(response.toString());
            return jo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getProductosxSucursal(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
        URL url = new URL("http://"+Config.ip + ":58706/api/Productos/ProductosxSucursal?id="+id );
        conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

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
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getProductoSucursal(int idSuc, int idProd){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Productos?Suc="+idSuc+"&CodProd="+idProd );
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONObject jArray=new JSONObject(response.toString());

            return jArray;

        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONArray getDirections(int type, int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        switch (type){
            case 1:
                try {
                    URL url = new URL("http://"+Config.ip + ":58706/api/Provincias/" );
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    URL url = new URL("http://"+Config.ip + ":58706/api/Cantones?idProvincia="+Integer.toString(id));
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    URL url = new URL("http://"+Config.ip + ":58706/api/Distrito?idCanton="+Integer.toString(id) );
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        return null;

    }

    public JSONArray getSucursales(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try{
            URL url = new URL("http://"+Config.ip + ":58706/api/Sucursal/" );
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

    public boolean regPadecimiento(JSONObject padecimiento){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Padecimientos");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(padecimiento.toString().getBytes());
            os.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public int regPedido(JSONObject pedido){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/Pedidos");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(pedido.toString().getBytes());
            os.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);

            }
            JSONObject jArray=new JSONObject(response.toString());
            return jArray.getInt("idPedido");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }


    }

    public boolean regDetalle(JSONObject pedido){

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection conn;
        try {
            URL url = new URL("http://"+Config.ip + ":58706/api/DetallePedido");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(pedido.toString().getBytes());
            os.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);

            }
            conn.disconnect();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public JSONArray getPadecimientos(int id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection conn;
        try{
            URL url = new URL("http://"+Config.ip + ":58706/api/Padecimientos/"+Integer.toString(id) );
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

    public void openLogin(){
        Toast.makeText(context,"Error to connect with server!, try changing the ip address.", Toast.LENGTH_SHORT).show();
        ///Intent intent = new Intent(this.context, MainActivity.class);
      //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(intent);
    }

}
