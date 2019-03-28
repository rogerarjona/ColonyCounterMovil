package com.arjona.roger.test2.Conexion;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.net.URI;
import java.util.Map;

public class SubirImagen extends AsyncTask<String, Long, Integer>  {

    Context context;
    Uri imagen;

    public SubirImagen(Context context, Uri imagen){
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
                    .option("tags", "TEST")
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {
                            Toast.makeText(context, "Upload Started...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {
                            // example code starts here
                            Double progress = (double) bytes / totalBytes;
                            //Toast.makeText(MainActivity.this, ""+progress, Toast.LENGTH_SHORT).show();
                            // post progress to app UI (e.g. progress bar, notification)
                            // example code ends here
                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            Toast.makeText(context, "Subido", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            System.out.println(error);

                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {
                            // your code here
                        }
                    })
                    .dispatch();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
        return 0;
    }
}
