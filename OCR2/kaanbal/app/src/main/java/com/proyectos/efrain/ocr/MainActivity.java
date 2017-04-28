package com.proyectos.efrain.ocr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Historial.OnFragmentInteractionListener,
        Ayuda.OnFragmentInteractionListener,Principal.OnFragmentInteractionListener {

    SharedPreferences sharedPreferences;
    android.support.v4.app.FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String menu="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Contenedor,new Principal()).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View view= navigationView.inflateHeaderView(R.layout.nav_header_main);


        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(menu.equals("historial")){
            getMenuInflater().inflate(R.menu.menu_historial, menu);
        }else {
            getMenuInflater().inflate(R.menu.main, menu);
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
        if (id == R.id.menu_compartir) {

        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_incio) {
            // Handle the camera action
            FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.Contenedor,new Principal()).commit();

        } else if (id == R.id.nav_historial) {
            FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
            xfragmentTransaction.replace(R.id.Contenedor,new Historial()).commit();
            menu="historial";
            MainMenu(menu);
        } else if (id == R.id.nav_ajustes) {

        } else if (id == R.id.nav_ayuda) {

        } else if (id == R.id.nav_sesion) {
            CerrarSesion();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private  String MainMenu(String menu){
        return menu;
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private  void CerrarSesion(){
        sharedPreferences.edit().clear().apply();

        Intent intent = new Intent(this,Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        /*
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();*/
    }
    private  void OpenCamera(Intent data){

    }
}
