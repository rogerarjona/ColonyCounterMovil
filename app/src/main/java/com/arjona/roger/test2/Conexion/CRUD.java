package com.arjona.roger.test2.Conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.arjona.roger.test2.Entidades.Proyecto;
import com.arjona.roger.test2.Proyectos.FragmentProyectos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CRUD {

    private static String webhook = "https://d6a5db6e.ngrok.io/";

    /////////////////////// PROYECTOS ///////////////////////////
    public static class crear_proyecto extends AsyncTask<List, Long, Integer>
    {

        @Override
        protected Integer doInBackground(List... list_data) {

            ArrayList<String> datos = (ArrayList<String>) list_data[0];
            JSONObject jsonObject = null;
            String response = "";
            int status = 0;

            try {

                jsonObject = new JSONObject();
                jsonObject.put("nombre", datos.get(0));
                jsonObject.put("usuario", datos.get(1));

                HttpRequest request = HttpRequest.get(webhook+"post-crear-nuevo-proyecto/").header("Content-Type","application/json").send(jsonObject.toString());
                status = request.code();
                response = request.body();

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + "json" + "\"");
            }
            return status;
        }

        protected void onPostExecute(Integer result) {

        }
    }

    public static class obtener_proyectos extends AsyncTask<List, Long, Integer>
    {
        private List<Proyecto> lista_proyectos = new ArrayList<>();
        private JSONObject jsonObject = null;
        private String response = "";
        private int status = 0;
        private View vista;
        private Context context;
        public obtener_proyectos(View vista, Context context) {
            this.vista = vista;
            this.context = context;
        }

        @Override
        protected Integer doInBackground(List... list_data) {

            ArrayList<String> datos = (ArrayList<String>) list_data[0];

            try {

                jsonObject = new JSONObject();
                jsonObject.put("nombre", datos.get(0));
                jsonObject.put("usuario", datos.get(1));

                HttpRequest request = HttpRequest.get(webhook+"post-obtener-proyecto/").header("Content-Type","application/json").send(jsonObject.toString());
                status = request.code();
                response = request.body();

                if (status == 200){
                    //Proceso de creacion del array del json
                    JSONObject object = new JSONObject(response); //Creamos un objeto JSON a partir de la cadena
                    JSONArray json_array = object.optJSONArray("proyectos");

                    for (int i=0; i<json_array.length(); i++){
                        lista_proyectos.add(new Proyecto(json_array.getJSONObject(i)));
                    }
                    System.out.print("");
                    return 1;
                }

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + "json" + "\"");
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            Log.e("OnPostExecute", "Result:" + result);
            if (result == 1){

                FragmentProyectos fragmentProyectos = new FragmentProyectos();
                fragmentProyectos.llenarListaProyectos((ArrayList) lista_proyectos);
                fragmentProyectos.setAdapterManual(vista, context);
                Log.e("OnPostExecute", "Lista:" + lista_proyectos.get(result).getNombre());
            }

        }
    }

    public static class editar_proyecto extends AsyncTask<List, Long, Integer>
    {

        @Override
        protected Integer doInBackground(List... lists) {
            return null;
        }
    }

    private class eliminar_proyecto extends AsyncTask<String, Long, Integer>
    {

        @Override
        protected Integer doInBackground(String... strings) {
            return null;
        }
    }

    /////////////////////// FOTOGRAFIAS ///////////////////////////

    /////////////////////// OTROS ///////////////////////////

}
