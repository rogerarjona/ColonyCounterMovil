package com.arjona.roger.test2.Entidades;

import org.json.JSONException;
import org.json.JSONObject;

public class ElementosFotografia {

    public String id_photo_colony, colony_form, colony_surface, colony_edge, colony_elevation, colony_growth, color, observacion;
    public int temperatura;
    public String photography_url_thumbnail, photography_counted_url_thumbnail;

    public ElementosFotografia(JSONObject elementos) throws JSONException {
        this.colony_form = elementos.getString("colony_form");
        this.colony_surface = elementos.getString("colony_surface");
        this.colony_edge = elementos.getString("colony_edge");
        this.colony_elevation = elementos.getString("colony_elevation");
        this.colony_growth = elementos.getString("colony_growth");
        this.color = elementos.getString("color");
        this.observacion = elementos.getString("observacion");
        this.temperatura = Integer.parseInt(elementos.getString("temperatura"));
    }

    public String getId_photo_colony() {
        return id_photo_colony;
    }

    public String getColony_form() {
        return colony_form;
    }

    public String getColony_surface() {
        return colony_surface;
    }

    public String getColony_edge() {
        return colony_edge;
    }

    public String getColony_elevation() {
        return colony_elevation;
    }

    public String getColony_growth() {
        return colony_growth;
    }

    public String getColor() {
        return color;
    }

    public String getObservacion() {
        return observacion;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public String getPhotography_url_thumbnail() {
        return photography_url_thumbnail;
    }

    public String getPhotography_counted_url_thumbnail() {
        return photography_counted_url_thumbnail;
    }

    public void setId_photo_colony(String id_photo_colony) {
        this.id_photo_colony = id_photo_colony;
    }

    public void setColony_form(String colony_form) {
        this.colony_form = colony_form;
    }

    public void setColony_surface(String colony_surface) {
        this.colony_surface = colony_surface;
    }

    public void setColony_edge(String colony_edge) {
        this.colony_edge = colony_edge;
    }

    public void setColony_elevation(String colony_elevation) {
        this.colony_elevation = colony_elevation;
    }

    public void setColony_growth(String colony_growth) {
        this.colony_growth = colony_growth;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public void setPhotography_url_thumbnail(String photography_url_thumbnail) {
        this.photography_url_thumbnail = photography_url_thumbnail;
    }

    public void setPhotography_counted_url_thumbnail(String photography_counted_url_thumbnail) {
        this.photography_counted_url_thumbnail = photography_counted_url_thumbnail;
    }
}
