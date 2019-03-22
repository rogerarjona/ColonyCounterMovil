package com.arjona.roger.test2;

import android.content.Intent;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arjona.roger.test2.Conexion.CRUD;
import com.arjona.roger.test2.Conexion.HttpRequest;
import com.cloudinary.android.MediaManager;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static Object o = null;
    //private FloatingActionButton atakephoto;
    private ImageView imageView;
    private String currentPhotoPath = null;
    private String imageFileName = "";
    private Button btnpost,atakephoto, buttonhttp;
    private EditText etmulti;
    private static final String TAG = "SampleActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        atakephoto = findViewById(R.id.takephoto);
        imageView = findViewById(R.id.imageView);
        etmulti = findViewById(R.id.etmulti);
        buttonhttp = findViewById(R.id.buttonhttp);
        buttonhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> datos = new ArrayList<String>();
                datos.add("1");
                datos.add("roger");



                new CRUD.crear_proyecto().execute(datos);
                System.out.println("");
            }
        });

        atakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dispatchTakePictureIntent();
                //onActivityResult();
                onSelectImageClick(v);
            }
        });
        btnpost = findViewById(R.id.button);
        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Para poder llamar la funcion asincronca podemos pasarle parametros
                //o = new test_http().execute("http://google.com");
                /*test_http.execute("http://google.com");
                myvalue = new myTask().execute().get();*/

                /*FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference docRef = db.collection("usuarios").document("rogerarjona");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                etmulti.setText(document.getData()+"\n");
                            } else {
                                Toast.makeText(MainActivity.this, "Error al obtener data", Toast.LENGTH_LONG).show();
                            }

                        }else {
                            Toast.makeText(MainActivity.this, "Otro Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });*/



                        /*.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity.this, "Usuario agregado correctamente", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        });*/

            }
        });

        //Posiblemente para inicializar cloudinary
        MediaManager.init(this);
    }

    /* Start pick image activity with chooser. USANDO EL BUILT-ING*/
    public void onSelectImageClick(View view) {
        CropImage.activity(null)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("CORTAR")
                .setCropShape(CropImageView.CropShape.OVAL)
                .setFixAspectRatio(true)
                .setAspectRatio(1,1 )
                .setAutoZoomEnabled(true)
                .setInitialCropWindowPaddingRatio(0)
                .start(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            System.out.println("TEST");

            if (resultCode == RESULT_OK) {
                ((ImageView) findViewById(R.id.imageView)).setImageURI(result.getUri());
                Toast.makeText(
                        this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class test_http extends AsyncTask<String, Long, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {

            int response = 0;
            //int response = HttpRequest.post("https://10d76004.ngrok.io/test/").send(jsonObject.toString()).code();
            //HttpRequest request = HttpRequest.post("https://10d76004.ngrok.io/test/");
            JSONObject jsonObject=null;
            try {
                jsonObject = new JSONObject();
                jsonObject.put("usuario", "roger");
                jsonObject.put("password", "temporal");

                Log.d("My App", jsonObject.toString());

                HttpRequest request = HttpRequest.get("https://80e23ecc.ngrok.io/get-test/").header("Content-Type","application/json").send(jsonObject.toString());
                response = request.code();
                String body = request.body();
                System.out.println("");

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + "json" + "\"");
            }

            return response;
        }

    }

}
