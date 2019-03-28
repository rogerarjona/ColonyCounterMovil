package com.arjona.roger.test2.Proyectos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arjona.roger.test2.Adaptadores.AdapterProyectos;
import com.arjona.roger.test2.Conexion.CRUD;
import com.arjona.roger.test2.Entidades.Proyecto;
import com.arjona.roger.test2.Entidades.Utils;
import com.arjona.roger.test2.Fotografias.FragmentFotografias;
import com.arjona.roger.test2.R;
import com.arjona.roger.test2.menu_app;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentProyectos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentProyectos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProyectos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ArrayList<Proyecto> listaProyectos;
    private RecyclerView recyclerProyectos;
    private String username;

    public FragmentProyectos() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProyectos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProyectos newInstance(String param1, String param2) {
        FragmentProyectos fragment = new FragmentProyectos();
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
        View vista  = inflater.inflate(R.layout.fragment_fragment_proyectos, container, false);
        username = Utils.getUserName();

        ArrayList<String> datos = new ArrayList<String>();
        datos.add(null);
        datos.add(username);

        listaProyectos = new ArrayList<>();

        CRUD.obtener_proyectos obj = new CRUD.obtener_proyectos(vista, getContext(), getFragmentManager());
        obj.execute(datos);

        FloatingActionButton floatingActionButton = ((menu_app) getActivity()).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.show();
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment_layout = new CrearProyectoFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragment_layout).commit();
            }
        });
        return vista;
    }

    public void setAdapterManual(View vista, final Context context, final FragmentManager manager){

        recyclerProyectos = vista.findViewById(R.id.recycler_proyectos);
        recyclerProyectos.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterProyectos adapterProyectos = new AdapterProyectos(listaProyectos);
        adapterProyectos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        "Seleccion:" + listaProyectos.get(recyclerProyectos.getChildAdapterPosition(v)).getNombre(), Toast.LENGTH_LONG).show();
                //Log.e("OnPostExecute", "Lista:" + listaProyectos.get(recyclerProyectos.getChildAdapterPosition(v)).getNombre());

                String id_proyecto_seleccionado = listaProyectos.get(recyclerProyectos.getChildAdapterPosition(v)).getId();
                Bundle bundle_envia = new Bundle();
                bundle_envia.putString("id_proyecto", id_proyecto_seleccionado);

                FragmentFotografias fragment_fotografias = new FragmentFotografias();
                fragment_fotografias.setArguments(bundle_envia);
                manager.beginTransaction().replace(R.id.content_main, fragment_fotografias).commit();

            }
        });
        recyclerProyectos.setAdapter(adapterProyectos);

    }

    public void llenarListaProyectos(ArrayList newlistaProyectos){
        listaProyectos = newlistaProyectos;
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
