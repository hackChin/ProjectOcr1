package com.proyectos.efrain.ocr.Util;

/**
 * Created by Efrain on 04/01/2017.
 */

public class Usuarios {

    String id;
    String nombre;
    String apellido;
    String correo;
    String telefono;
    String localidad;
    String perfil;

    public Usuarios(String id, String nombre, String apellido, String correo, String telefono, String localidad, String perfil) {
        this.id=id;
        this.nombre=nombre;
        this.apellido=apellido;
        this.correo=correo;
        this.telefono=telefono;
        this.localidad=localidad;
        this.perfil=perfil;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
