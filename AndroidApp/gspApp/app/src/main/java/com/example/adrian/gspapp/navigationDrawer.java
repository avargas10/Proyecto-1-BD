package com.example.adrian.gspapp;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.gspapp.Tools.Config;

import org.json.JSONException;

import java.util.Calendar;

public class navigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView menuName, menuUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(2131624200);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        menuName=(TextView)findViewById(R.id.textView2);
        menuUser=(TextView)findViewById(R.id.textView);

        try {

            menuName.setText(MainActivity.clientInfo.getString("Nombre")
            +" "+MainActivity.clientInfo.getString("pApellido")
            +" "+MainActivity.clientInfo.getString("sApellido"));
            menuUser.setText(MainActivity.clientInfo.getString("Username"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int id){
        Fragment fragment=null;
        switch (id){
            case R.id.nav_camera:
                fragment=new Home();
                break;
            case R.id.nav_gallery:
                fragment=new Recetas();
                break;
            case R.id.nav_padecimientos:
                fragment=new Padecimientos();
                break;
            case R.id.nav_manage:
                fragment=new Cuenta();
                break;
            case  R.id.nav_slideshow:
                fragment=new Pedidos();
                break;
        }
        if(fragment!=null){
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_principal, fragment);
            ft.commit();
            }
        DrawerLayout drawer =(DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_share) {
            Intent intent = new Intent(navigationDrawer.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
             displaySelectedScreen(id);
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void configip(MenuItem item) {
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
