package com.arjona.roger.test2.Conexion;

import org.json.JSONException;
import org.json.JSONObject;

public class Proyecto {

    public String nombre;
    public String usuario;
    public String id;

    public Proyecto(JSONObject proyecto) throws JSONException {
        this.nombre = proyecto.getString("nombre");
        this.usuario = proyecto.getString("usuario");
        this.id = proyecto.getString("id");
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

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getId() {
        return id;
    }

}
