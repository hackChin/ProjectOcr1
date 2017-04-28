package com.proyectos.efrain.ocr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.proyectos.efrain.ocr.Util.Util;

public class Splash extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences= getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        Inicio();
    }


    private  void Inicio()  {

        if(!TextUtils.isEmpty(Util.UserEmail(sharedPreferences)) && !TextUtils.isEmpty(Util.UserPassword(sharedPreferences))){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

    }
}
