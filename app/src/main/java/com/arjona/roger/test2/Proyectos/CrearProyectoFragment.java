package com.arjona.roger.test2.Proyectos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arjona.roger.test2.Conexion.CRUD;
import com.arjona.roger.test2.Entidades.Utils;
import com.arjona.roger.test2.R;
import com.arjona.roger.test2.menu_app;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CrearProyectoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CrearProyectoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearProyectoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private String username;
    private ProgressBar progressBar;

    public CrearProyectoFragment() {
        // Required empty public constructor
        username = Utils.getUserName();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearProyectoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearProyectoFragment newInstance(String param1, String param2) {
        CrearProyectoFragment fragment = new CrearProyectoFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vista = inflater.inflate(R.layout.fragment_crear_proyecto, container, false);
        Button btncrearproyecto = vista.findViewById(R.id.btncrearproyecto);
        final EditText etnombre_proyecto = vista.findViewById(R.id.etnombre_proyecto);
        final EditText et_descripcion_proyecto = vista.findViewById(R.id.et_descripcion_proyecto);
        progressBar = vista.findViewById(R.id.progressbar);

        FloatingActionButton floatingActionButton = ((menu_app) getActivity()).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Estas en crear proyecto", Toast.LENGTH_LONG).show();
                }
            });
        }

        btncrearproyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre_proyecto = etnombre_proyecto.getText().toString();
                String descripcion_proyecto = et_descripcion_proyecto.getText().toString();

                if(TextUtils.isEmpty(nombre_proyecto)){
                    etnombre_proyecto.setError("Escriba un nombre!");
                    return;
                }
                if (TextUtils.isEmpty(descripcion_proyecto)){
                    descripcion_proyecto = "";
                }

                ArrayList<String> datos = new ArrayList<String>();
                datos.add(nombre_proyecto);
                datos.add(descripcion_proyecto);
                datos.add(username);
                CRUD.crear_proyecto crear_proyecto = new CRUD.crear_proyecto(vista, getFragmentManager());
                hacerVisible();
                crear_proyecto.execute(datos);
                //Toast.makeText(getContext(), nombre_proyecto, Toast.LENGTH_LONG).show();
            }
        });

        return vista;
    }

    public void hacerVisible(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hacerInvisible(View v){
        ProgressBar progressBar = v.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
    }

    public void regresarMenuProyectos(FragmentManager manager){
        Fragment fragment_layout = new FragmentProyectos();
        manager.beginTransaction().replace(R.id.content_main, fragment_layout).commit();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
