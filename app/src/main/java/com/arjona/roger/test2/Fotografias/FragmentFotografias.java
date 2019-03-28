package com.arjona.roger.test2.Fotografias;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.arjona.roger.test2.Adaptadores.AdapterFotografias;
import com.arjona.roger.test2.Adaptadores.AdapterProyectos;
import com.arjona.roger.test2.Conexion.CRUD;
import com.arjona.roger.test2.Conexion.SubirImagen;
import com.arjona.roger.test2.Entidades.Fotografias;
import com.arjona.roger.test2.Entidades.Proyecto;
import com.arjona.roger.test2.Entidades.Utils;
import com.arjona.roger.test2.FragmentDatosFotografias;
import com.arjona.roger.test2.Proyectos.CrearProyectoFragment;
import com.arjona.roger.test2.R;
import com.arjona.roger.test2.menu_app;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentFotografias.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFotografias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFotografias extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ArrayList<Fotografias> listaFotografias;
    private RecyclerView recyclerFotografias;

    //Datos para el view
    private String username;
    private String id_proyecto;

    public FragmentFotografias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFotografias.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFotografias newInstance(String param1, String param2) {
        FragmentFotografias fragment = new FragmentFotografias();
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
        id_proyecto = bundle_recibe.getString("id_proyecto");

        try{
            MediaManager.init(getContext());
        }catch (Exception e){

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista  = inflater.inflate(R.layout.fragment_fragment_fotografias, container, false);
        username = Utils.getUserName();

        ArrayList<String> datos = new ArrayList<String>();
        datos.add(id_proyecto);
        datos.add(username);

        listaFotografias = new ArrayList<>();
        CRUD.fotografias_proyecto fotos = new CRUD.fotografias_proyecto(vista, getContext(), getFragmentManager());
        fotos.execute(datos);

        FloatingActionButton floatingActionButton = ((menu_app) getActivity()).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.show();
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
            }
        });

        return vista;
    }

    public void setAdapterManual(View vista, final Context context, final FragmentManager manager){

        recyclerFotografias = vista.findViewById(R.id.recycler_fotografias);
        recyclerFotografias.setLayoutManager(new GridLayoutManager(getContext(),2));

        AdapterFotografias adapter = new AdapterFotografias(listaFotografias);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_photo_colony = listaFotografias.get(recyclerFotografias.getChildAdapterPosition(v)).getId();
                Bundle bundle_envia = new Bundle();
                bundle_envia.putString("id_photo_colony", id_photo_colony);

                FragmentDatosFotografias detallesFotografia = new FragmentDatosFotografias();
                detallesFotografia.setArguments(bundle_envia);
                manager.beginTransaction().replace(R.id.content_main, detallesFotografia).commit();
            }
        });
        recyclerFotografias.setAdapter(adapter);

    }

    public void llenarListaFotografias(ArrayList newLista){
        listaFotografias = newLista;
    }

    public void onSelectImageClick(View view) {
        CropImage.activity(null)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("CROP")
                .setCropShape(CropImageView.CropShape.OVAL)
                .setFixAspectRatio(true)
                .setAspectRatio(1,1 )
                .setAutoZoomEnabled(true)
                .setInitialCropWindowPaddingRatio(0)
                .start(getContext(),this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                SubirImagenTest subirImagen = new SubirImagenTest(getContext(), result.getUri());
                subirImagen.execute();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getContext(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class SubirImagenTest extends AsyncTask<String, Long, Integer> {

        Context context;
        Uri imagen;

        public SubirImagenTest(Context context, Uri imagen){
            this.context = context;
            this.imagen = imagen;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                //cloudinary.uploader().upload(currentPhotoPath, ObjectUtils.emptyMap());¿
                String requestId = MediaManager.get().upload(imagen)
                        .unsigned("u72wk4gq")
                        .option("connect_timeout", 10000)
                        .option("read_timeout", 10000)
                        .option("tags", "imagen##"+username+"##"+id_proyecto)
                        .callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {

                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {

                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {

                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {

                            }
                        })
                        .dispatch();
            }catch (Exception e){
                System.out.print(e.getMessage());
                System.out.print("");
            }
            return 0;
        }
    }
}
