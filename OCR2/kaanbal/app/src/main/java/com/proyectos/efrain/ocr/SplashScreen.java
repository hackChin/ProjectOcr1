package com.proyectos.efrain.ocr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.proyectos.efrain.ocr.Util.Util;

public class SplashScreen extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        sharedPreferences= getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        try {
            Inicio();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private  void Inicio() throws InterruptedException {

        if(!TextUtils.isEmpty(Util.UserEmail(sharedPreferences)) && !TextUtils.isEmpty(Util.UserPassword(sharedPreferences))){
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
           startActivity(intent);
        }else{
            Intent intent = new Intent(SplashScreen.this, Login.class);
            startActivity(intent);
        }

    }
}
