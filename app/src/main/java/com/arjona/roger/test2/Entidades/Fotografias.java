package com.arjona.roger.test2.Entidades;

import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import androidx.annotation.RequiresApi;

public class Fotografias {

    public String url_imagen;
    public String id;
    public String fecha;


    public Fotografias(JSONObject proyecto) throws JSONException {
        this.url_imagen = proyecto.getString("url_thumbnail");
        this.fecha = proyecto.getString("created");
        this.id = proyecto.getString("id_imagen");
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public String getId() {
        return id;
    }

    public String getFecha(){
        String fecha_hora = "";
        String[] partes = fecha.split("[T,\\+]");
        fecha_hora = partes[0] + " " + partes[1];
        return fecha_hora;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
