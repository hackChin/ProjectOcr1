package com.proyectos.efrain.ocr.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by emore on 29/11/2016.
 */

public abstract class Adaptador extends BaseAdapter {

    private ArrayList<Usuarios> datos;
    private int R_Layout_lista;
    //Objeto de tipo contexto
    private Context contexto;

    //Constructor de la clase que recibe el contexto, el layout y un arraylist lleno
    public Adaptador(Context contexto, int R_Layout_lista, ArrayList<Usuarios> datos) {
        super();
        //Se asignan a las variables locales las que recibe el constructor
        this.contexto = contexto;
        this.datos = datos;
        this.R_Layout_lista=R_Layout_lista;
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return datos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R_Layout_lista, null);
        }
        onEntrada (datos.get(position), convertView);
        return convertView;
    }

    public abstract void onEntrada (Object entrada, View view);
}
