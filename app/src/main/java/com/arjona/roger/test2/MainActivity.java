package com.arjona.roger.test2;

import android.content.Intent;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static Object o = null;
    //private FloatingActionButton atakephoto;
    private ImageView imageView;
    private String currentPhotoPath = null;
    private String imageFileName = "";
    private Button btnpost,atakephoto;
    private EditText etmulti;
    private static final String TAG = "SampleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        atakephoto = findViewById(R.id.takephoto);
        imageView = findViewById(R.id.imageView);
        etmulti = findViewById(R.id.etmulti);
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

                FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                });



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


}
