package com.fundacionrescate.rescata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.model.Comentario;
import com.fundacionrescate.rescata.model.Donacion;

import java.util.ArrayList;

public class Comentarios  extends RecyclerView.Adapter<Comentarios.ViewHolder>{

    private final ArrayList<Comentario> mValues;
    Context context;
    public Comentarios( ArrayList<Comentario> items, Context context) {
        mValues = items;
        this.context = context;
    }


    @Override
    public Comentarios.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comentario, parent, false);
        return new Comentarios.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final Comentarios.ViewHolder holder, final int position) {
//        holder.categoria.setText(mValues.get(position).getCategoria());
//        holder.detalle.setText(mValues.get(position).getDetalle());

        holder.comentario.setText(mValues.get(position).getComentario());

        holder.usuario.setText(mValues.get(position).getUsuario());

    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView usuario;
        final TextView comentario;
//        final TextView tipo;

        public ViewHolder(View view) {
            super(view);
            usuario = view.findViewById(R.id.usuario);
            comentario = view.findViewById(R.id.comentario);
//            tipo = view.findViewById(R.id.tipo);

        }
    }
}