package com.arjona.roger.test2.Conexion;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.arjona.roger.test2.Entidades.ElementosFotografia;
import com.arjona.roger.test2.Entidades.Fotografias;
import com.arjona.roger.test2.Entidades.Proyecto;
import com.arjona.roger.test2.Fotografias.FragmentFotografias;
import com.arjona.roger.test2.FragmentDatosFotografias;
import com.arjona.roger.test2.Proyectos.CrearProyectoFragment;
import com.arjona.roger.test2.Proyectos.FragmentProyectos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;

public class CRUD {

    private static String webhook = "https://78d02809.ngrok.io/";

    /////////////////////// PROYECTOS ///////////////////////////
    public static class crear_proyecto extends AsyncTask<List, Long, Integer>
    {
        View vista;
        FragmentManager manager;

        public crear_proyecto(View vista, FragmentManager manager ){
            this.vista = vista;
            this.manager = manager;
        }

        @Override
        protected Integer doInBackground(List... list_data) {

            ArrayList<String> datos = (ArrayList<String>) list_data[0];
            JSONObject jsonObject = null;
            String response = "";
            int status = 0;

            try {

                jsonObject = new JSONObject();
                jsonObject.put("nombre", datos.get(0));
                jsonObject.put("descripcion", datos.get(1));
                jsonObject.put("usuario", datos.get(2));

                HttpRequest request = HttpRequest.get(webhook+"post-crear-nuevo-proyecto/").header("Content-Type","application/json").send(jsonObject.toString());
                status = request.code();
                response = request.body();

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + "json" + "\"");
            }
            return status;
        }

        protected void onPostExecute(Integer result) {
            CrearProyectoFragment fragment = new CrearProyectoFragment();
            fragment.hacerInvisible(vista);
            fragment.regresarMenuProyectos(manager);
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
        private FragmentManager manager;
        public obtener_proyectos(View vista, Context context, FragmentManager manager) {
            this.vista = vista;
            this.context = context;
            this.manager = manager;
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
                fragmentProyectos.setAdapterManual(vista, context, manager);

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
    public static class fotografias_proyecto extends AsyncTask<List, Long, Integer>{

        private List<Fotografias> lista_fotografias = new ArrayList<>();
        private JSONObject jsonObject = null;
        private String response = "";
        private int status = 0;

        private View vista;
        private Context context;
        private FragmentManager manager;

        public fotografias_proyecto(View vista, Context context, FragmentManager manager) {
            this.vista = vista;
            this.context = context;
            this.manager = manager;
        }

        @Override
        protected Integer doInBackground(List... lists) {
            ArrayList<String> datos = (ArrayList<String>) lists[0];
            try{

                jsonObject = new JSONObject();
                jsonObject.put("id_proyecto", datos.get(0));
                jsonObject.put("usuario", datos.get(1));

                HttpRequest request = HttpRequest.get(webhook+"post-obtener-imagenes-proyecto/").header("Content-Type","application/json").send(jsonObject.toString());
                status = request.code();
                response = request.body();

                if (status == 200){
                    //Proceso de creacion del array del json
                    JSONObject object = new JSONObject(response); //Creamos un objeto JSON a partir de la cadena
                    JSONArray json_array = object.optJSONArray("imagenes_proyectos");

                    for (int i=0; i<json_array.length(); i++){
                        lista_fotografias.add(new Fotografias(json_array.getJSONObject(i)));
                    }
                    System.out.print("");
                    return 1;
                }

            }catch (Exception e){
                Log.e("fotografias_proyecto", e.getMessage()+"");
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            Log.e("OnPostExecute", "Result:" + result);
            if (result == 1){

                FragmentFotografias fragment = new FragmentFotografias();
                fragment.llenarListaFotografias((ArrayList) lista_fotografias);
                fragment.setAdapterManual(vista, context, manager);
            }

        }
    }


    ///////////// COLONY ELEMENTS /////////////////
    public static class actualizar_colony_elements extends AsyncTask<List, Long, Integer>{

        @Override
        protected Integer doInBackground(List... lists) {

            ArrayList<String> datos = (ArrayList<String>) lists[0];
            JSONObject jsonObject = null;
            String response = "";
            int status = 0;

            try {

                jsonObject = new JSONObject();
                jsonObject.put("id_photo_colony", datos.get(0));
                jsonObject.put("username", datos.get(1));
                jsonObject.put("colony_form", datos.get(2));
                jsonObject.put("colony_surface", datos.get(3));
                jsonObject.put("colony_edge", datos.get(4));
                jsonObject.put("colony_elevation", datos.get(5));
                jsonObject.put("colony_growth", datos.get(6));
                jsonObject.put("color", datos.get(7));
                jsonObject.put("temperatura", datos.get(8));
                jsonObject.put("observaciones", datos.get(9));

                HttpRequest request = HttpRequest.get(webhook+"post-actualizar-colony-elements/").header("Content-Type","application/json").send(jsonObject.toString());
                status = request.code();
                response = request.body();

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + "json" + "\"");
            }
            return status;
        }
    }

    public static class obtener_colony_elements extends AsyncTask<List, Long, Integer>{
        View vista;
        public obtener_colony_elements(View vista){
            this.vista = vista;
        }

        private List<ElementosFotografia> lista_elementos_fotografia = new ArrayList<>();

        @Override
        protected Integer doInBackground(List... lists) {
            ArrayList<String> datos = (ArrayList<String>) lists[0];
            JSONObject jsonObject = null;
            String response = "";
            int status = 0;

            try {

                jsonObject = new JSONObject();
                jsonObject.put("id_photo_colony", datos.get(0));

                HttpRequest request = HttpRequest.get(webhook+"post-obtener-colony-elements/").header("Content-Type","application/json").send(jsonObject.toString());
                status = request.code();

                if (status == 200){
                    response = request.body();
                    //Proceso de creacion del array del json
                    JSONObject object = new JSONObject(response); //Creamos un objeto JSON a partir de la cadena
                    JSONArray json_array = object.optJSONArray("colony_elements");

                    lista_elementos_fotografia.add(new ElementosFotografia(json_array.getJSONObject(0)));
                    System.out.print("");

                }

                return 1;
            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + "json" + "\"");
            }

            return  0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1){

                FragmentDatosFotografias fragmentDatosFotografias = new FragmentDatosFotografias();
                fragmentDatosFotografias.llenarDatos((ArrayList) lista_elementos_fotografia, vista);
            }

        }


    }

    /////////////////////// OTROS ///////////////////////////

}
