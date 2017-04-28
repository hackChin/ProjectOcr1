package com.proyectos.efrain.ocr.Util;

import android.content.SharedPreferences;

/**
 * Created by Efrain on 22/03/2017.
 */
public class Util {
    public  static String UserEmail(SharedPreferences sharedPreferences){
        return sharedPreferences.getString("email","");
    }

    public static String UserPassword(SharedPreferences sharedPreferences){
        return sharedPreferences.getString("password","");
    }

    public static String PicturePatch(SharedPreferences sharedPreferences){
        return sharedPreferences.getString("patch","");
    }
}