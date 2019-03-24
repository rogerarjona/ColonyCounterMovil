package com.arjona.roger.test2.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arjona.roger.test2.Entidades.Proyecto;
import com.arjona.roger.test2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterProyectos extends RecyclerView.Adapter<AdapterProyectos.ViewHolderProyectos> implements View.OnClickListener {


    //El metodo recibira una ArraList del tipo Proyecto que es un POJO que crea y devuelve los atributos que le colocamos
    ArrayList<Proyecto> listaProyectos;
    private View.OnClickListener listener;

    public AdapterProyectos(ArrayList<Proyecto> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    @Override
    public ViewHolderProyectos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Se instancia a si mismo colocandose en el layout que le digamos
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_proyectos, null, false);
        //Se agrega un listener para la accion de abrir elemento
        view.setOnClickListener(this);

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

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    //Esta clase constructor sirve para instanciar los objetos a utilizar en el xml, y son utilizados por el OnBindViewHolder
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
