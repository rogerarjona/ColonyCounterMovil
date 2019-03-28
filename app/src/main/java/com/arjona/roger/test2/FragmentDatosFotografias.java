package com.arjona.roger.test2;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.arjona.roger.test2.Adaptadores.AdapterProyectos;
import com.arjona.roger.test2.Conexion.CRUD;
import com.arjona.roger.test2.Entidades.ElementosFotografia;
import com.arjona.roger.test2.Entidades.Fotografias;
import com.arjona.roger.test2.Entidades.Utils;
import com.arjona.roger.test2.Fotografias.FragmentFotografias;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDatosFotografias.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDatosFotografias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDatosFotografias extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /*Spinners y elementos de interaccion*/
    Spinner COLONY_FORM, COLONY_SURFACE, COLONY_EDGE, COLONY_ELEVATION, COLONY_GROWTH;
    EditText TEMPERATURA, COLOR, OBSERVACIONES;
    Button buton_save_colony_elements;

    private String colony_form = "", colony_surface = "", colony_edge = "", colony_elevation = "", colony_growth = "";
    private String username, id_photo_colony;

    ArrayList<ElementosFotografia> detallesFotografia;


    public FragmentDatosFotografias() {
        username = Utils.getUserName();
    }

    public static FragmentDatosFotografias newInstance(String param1, String param2) {
        FragmentDatosFotografias fragment = new FragmentDatosFotografias();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Bundle bundle_recibe = this.getArguments();
        id_photo_colony = bundle_recibe.getString("id_photo_colony");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_fragment_datos_fotografias, container, false);
        COLONY_FORM = vista.findViewById(R.id.spinner_forma);
        COLONY_SURFACE = vista.findViewById(R.id.spinner_superficie);
        COLONY_EDGE = vista.findViewById(R.id.spinner_borde);
        COLONY_ELEVATION = vista.findViewById(R.id.spinner_elevacion);
        COLONY_GROWTH = vista.findViewById(R.id.spinner_crecimiento);

        ArrayAdapter<CharSequence> adapter_colony_form = ArrayAdapter.createFromResource(getContext(), R.array.COLONY_FORM, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter_colony_surface = ArrayAdapter.createFromResource(getContext(), R.array.COLONY_SURFACE, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_colony_edge = ArrayAdapter.createFromResource(getContext(), R.array.COLONY_EDGE, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_colony_elevation = ArrayAdapter.createFromResource(getContext(), R.array.COLONY_ELEVATION, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_colony_growth = ArrayAdapter.createFromResource(getContext(), R.array.COLONY_GROWTH, android.R.layout.simple_spinner_item);

        COLONY_FORM.setAdapter(adapter_colony_form);
        COLONY_SURFACE.setAdapter(adapter_colony_surface);
        COLONY_EDGE.setAdapter(adapter_colony_edge);
        COLONY_ELEVATION.setAdapter(adapter_colony_elevation);
        COLONY_GROWTH.setAdapter(adapter_colony_growth);

        TEMPERATURA = vista.findViewById(R.id.et_temperatura);
        COLOR = vista.findViewById(R.id.et_color);
        OBSERVACIONES = vista.findViewById(R.id.et_observaciones);

        COLONY_FORM.setOnItemSelectedListener(this);
        COLONY_SURFACE.setOnItemSelectedListener(this);
        COLONY_EDGE.setOnItemSelectedListener(this);
        COLONY_ELEVATION.setOnItemSelectedListener(this);
        COLONY_GROWTH.setOnItemSelectedListener(this);

        buton_save_colony_elements = vista.findViewById(R.id.btn_guardar_colony_elements);

        ArrayList<String> datos = new ArrayList<String>();
        datos.add(id_photo_colony);
        CRUD.obtener_colony_elements obtener_colony_elements = new CRUD.obtener_colony_elements(vista);
        obtener_colony_elements.execute(datos);

        FloatingActionButton floatingActionButton = ((menu_app) getActivity()).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();
        }

        buton_save_colony_elements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String color, descripcion,temperatura;
                Integer int_temperatura;

                color = COLOR.getText().toString();
                descripcion = OBSERVACIONES.getText().toString();
                temperatura = TEMPERATURA.getText().toString();

                if(TextUtils.isEmpty(color)){
                    color = "";
                }
                if(TextUtils.isEmpty(temperatura)){
                    int_temperatura = 0;
                }else{
                    int_temperatura = Integer.parseInt(temperatura);
                }

                ArrayList<String> datos = new ArrayList<String>();
                datos.add(id_photo_colony);
                datos.add(username);
                datos.add(colony_form);
                datos.add(colony_surface);
                datos.add(colony_edge);
                datos.add(colony_elevation);
                datos.add(colony_growth);
                datos.add(color);
                datos.add(temperatura);
                datos.add(descripcion);

                CRUD.actualizar_colony_elements actualizar_colony_elements = new CRUD.actualizar_colony_elements();
                actualizar_colony_elements.execute(datos);
            }
        });

        return vista;
    }

    public void llenarDatos(ArrayList newlistaDatos, View vista){
        detallesFotografia = newlistaDatos;

        COLONY_FORM = vista.findViewById(R.id.spinner_forma);
        COLONY_SURFACE = vista.findViewById(R.id.spinner_superficie);
        COLONY_EDGE = vista.findViewById(R.id.spinner_borde);
        COLONY_ELEVATION = vista.findViewById(R.id.spinner_elevacion);
        COLONY_GROWTH = vista.findViewById(R.id.spinner_crecimiento);

        TEMPERATURA = vista.findViewById(R.id.et_temperatura);
        COLOR = vista.findViewById(R.id.et_color);
        OBSERVACIONES = vista.findViewById(R.id.et_observaciones);

        ArrayAdapter adapter_colony_form = (ArrayAdapter) COLONY_FORM.getAdapter();
        ArrayAdapter adapter_colony_surface = (ArrayAdapter) COLONY_SURFACE.getAdapter();
        ArrayAdapter adapter_colony_edge = (ArrayAdapter) COLONY_EDGE.getAdapter();
        ArrayAdapter adapter_colony_elevation = (ArrayAdapter) COLONY_ELEVATION.getAdapter();
        ArrayAdapter adapter_colony_growth = (ArrayAdapter) COLONY_GROWTH.getAdapter();

        int colony_form_position = adapter_colony_form.getPosition(detallesFotografia.get(0).getColony_form());
        int colony_form_surface = adapter_colony_surface.getPosition(detallesFotografia.get(0).getColony_surface());
        int colony_form_edge = adapter_colony_edge.getPosition(detallesFotografia.get(0).getColony_edge());
        int colony_form_elevation = adapter_colony_elevation.getPosition(detallesFotografia.get(0).getColony_elevation());
        int colony_form_growth = adapter_colony_growth.getPosition(detallesFotografia.get(0).getColony_growth());

        COLONY_FORM.setSelection(colony_form_position);
        COLONY_SURFACE.setSelection(colony_form_surface);
        COLONY_EDGE.setSelection(colony_form_edge);
        COLONY_ELEVATION.setSelection(colony_form_elevation);
        COLONY_GROWTH.setSelection(colony_form_growth);

        TEMPERATURA.setText(detallesFotografia.get(0).getTemperatura()+"");
        COLOR.setText(detallesFotografia.get(0).getColor()+"");
        OBSERVACIONES.setText(detallesFotografia.get(0).getObservacion()+"");

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinner_forma:
                switch ((String) parent.getItemAtPosition(position)){
                    case "Puntiforme":
                        colony_form = "1";
                        break;
                    case "Circular":
                        colony_form = "2";
                        break;
                    case "Irregular":
                        colony_form = "3";
                        break;
                    case "Rizoide":
                        colony_form = "4";
                        break;
                    case "Fusiforme":
                        colony_form = "5";
                        break;
                }
                break;
            case R.id.spinner_superficie:
                switch ((String) parent.getItemAtPosition(position)) {
                    case "Lisa":
                        colony_surface = "1";
                        break;
                    case "Rugosa":
                        colony_surface = "2";
                        break;
                    case "Plegada":
                        colony_surface = "3";
                        break;
                }
                break;
            case R.id.spinner_borde:
                switch ((String) parent.getItemAtPosition(position)) {
                    case "Redondeado":
                        colony_edge = "1";
                        break;
                    case "Ondulado":
                        colony_edge = "2";
                        break;
                    case "Lobulado":
                        colony_edge = "3";
                        break;
                    case "Filamentoso":
                        colony_edge = "4";
                        break;
                }
                break;
            case R.id.spinner_crecimiento:
                switch ((String) parent.getItemAtPosition(position)) {
                    case "Sin Crecimiento":
                        colony_growth  = "1";
                        break;
                    case "Crecimiento Regular":
                        colony_growth  = "2";
                        break;
                    case "Crecimient Abundante":
                        colony_growth  = "3";
                        break;
                }
                break;
            case R.id.spinner_elevacion:
                switch ((String) parent.getItemAtPosition(position)) {
                    case "Plana":
                        colony_elevation = "1";
                        break;
                    case "Convexa":
                        colony_elevation = "2";
                        break;
                    case "Elevada":
                        colony_elevation = "3";
                        break;
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
