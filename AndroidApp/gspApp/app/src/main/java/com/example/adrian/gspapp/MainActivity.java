package com.example.adrian.gspapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.gspapp.Tools.Config;
import com.example.adrian.gspapp.Tools.Connection;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button login;
    TextView forgot, registro;
    EditText username, password;
    private ProgressBar pb;
    public  static JSONObject clientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Gas Station Pharmacy");

        login=(Button) findViewById(R.id.btnLogin);
        forgot=(TextView) findViewById(R.id.textForgot);
        registro=(TextView) findViewById(R.id.textSignup);
        username=(EditText) findViewById(R.id.txtUsername);
        password=(EditText) findViewById(R.id.txtPassword);
        pb=(ProgressBar)findViewById(R.id.progressBar1);
        pb.setVisibility(View.GONE);
        final Context context=this.getApplicationContext();
        Connection.getInstance().context=context;


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Enter your username to send your password to your email")
                        .setTitle("Password Recovery");
                final EditText input = new EditText(context);
                builder.setView(input);
                builder.setPositiveButton("SEND",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                Toast.makeText(getApplicationContext(),"Email Sended", Toast.LENGTH_SHORT).show();

                            }
                        });

                builder.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, Registro.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                if(Connection.getInstance().clientLogin(username.getText().toString(),
                        password.getText().toString())){
                    username.setText("");
                    password.setText("");
                    Intent intent = new Intent(context, navigationDrawer.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Usuario o Contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                }
                pb.setVisibility(View.GONE);
            }
        });


    }


    public void ipconfig(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("enter your ip settings")
                .setTitle("IP Config");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("SEND",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Toast.makeText(getApplicationContext(),"Ip Changed", Toast.LENGTH_SHORT).show();
                        Config.ip = input.getText().toString();
                        if(Connection.getInstance().isConnectedToServer("http://"+Config.ip+":58706")){

                        }else{
                            Toast.makeText(getApplicationContext(),"Error to connect with server!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        builder.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
