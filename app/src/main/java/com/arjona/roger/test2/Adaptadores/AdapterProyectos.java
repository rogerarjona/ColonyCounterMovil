package com.arjona.roger.test2.Adaptadores;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arjona.roger.test2.Entidades.Proyecto;
import com.arjona.roger.test2.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterProyectos extends RecyclerView.Adapter<AdapterProyectos.ViewHolderProyectos> {

    ArrayList<Proyecto> listaProyectos;
    public AdapterProyectos(ArrayList<Proyecto> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    @Override
    public ViewHolderProyectos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_proyectos, null, false);
        return new ViewHolderProyectos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProyectos holder, int position) {
        
        String url_imagen = listaProyectos.get(position).getUrl_imagen();
        holder.titulo_proyecto.setText(listaProyectos.get(position).getNombre());
        holder.descripcion_proyecto.setText(listaProyectos.get(position).getDescripcion());
        Picasso.get().load(url_imagen).into(holder.imagen_proyecto);
    }

    @Override
    public int getItemCount() {
        return listaProyectos.size();
    }

    public class ViewHolderProyectos extends RecyclerView.ViewHolder {

        ImageView imagen_proyecto;
        TextView titulo_proyecto, descripcion_proyecto;

        public ViewHolderProyectos(@NonNull View itemView) {
            super(itemView);
            imagen_proyecto = itemView.findViewById(R.id.proyecto_image);
            titulo_proyecto = itemView.findViewById(R.id.titulo_proyecto_id);
            descripcion_proyecto = itemView.findViewById(R.id.descripcion_proyecto);

        }
    }
}
