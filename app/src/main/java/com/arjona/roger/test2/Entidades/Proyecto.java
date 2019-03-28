package com.arjona.roger.test2.Entidades;

import org.json.JSONException;
import org.json.JSONObject;

public class Proyecto {

    public String id;
    public String nombre;
    public String usuario;
    public String descripcion;
    public String url_imagen;

    public Proyecto(JSONObject proyecto) throws JSONException {
        this.nombre = proyecto.getString("nombre");
        this.usuario = proyecto.getString("usuario");
        this.id = proyecto.getString("id");
        this.descripcion = proyecto.getString("descripcion");
        this.url_imagen = proyecto.getString("url");
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public void setUrl_imagen(String url_imagen) { this.url_imagen = url_imagen;}

    public String getUrl_imagen() { return url_imagen;}

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() { return descripcion; }


}
