package com.arjona.roger.test2.Adaptadores;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arjona.roger.test2.Entidades.Fotografias;
import com.arjona.roger.test2.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterFotografias extends RecyclerView.Adapter<AdapterFotografias.ViewHolderFotografias> implements View.OnClickListener{

    ArrayList<Fotografias> listaFotografias;
    private View.OnClickListener listener;

    public AdapterFotografias(ArrayList<Fotografias> listaFotografias) {
        this.listaFotografias = listaFotografias;
    }


    @Override
    public ViewHolderFotografias onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_fotografias, null, false);
        view.setOnClickListener(this);
        return new ViewHolderFotografias(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFotografias holder, int position) {
        String url_thumbnail = listaFotografias.get(position).getUrl_imagen();
        Picasso.get().load(url_thumbnail).into(holder.thumbnail_fotografia);
        holder.fecha_fotografia.setText(listaFotografias.get(position).getFecha());

    }

    @Override
    public int getItemCount() {
        return listaFotografias.size();
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

    public class ViewHolderFotografias extends RecyclerView.ViewHolder {

        ImageView thumbnail_fotografia;
        TextView fecha_fotografia;
        public ViewHolderFotografias(@NonNull View itemView) {
            super(itemView);
            thumbnail_fotografia = itemView.findViewById(R.id.fotografia_imagen_list);
            fecha_fotografia = itemView.findViewById(R.id.fecha_imagen_list);
        }
    }
}
