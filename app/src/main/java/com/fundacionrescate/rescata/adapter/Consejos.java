package com.fundacionrescate.rescata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.model.Consejo;

import java.util.ArrayList;

public class Consejos extends RecyclerView.Adapter<Consejos.ViewHolder>{

    private final ArrayList<Consejo> mValues;
    Context context;
    public Consejos( ArrayList<Consejo> items, Context context) {
        mValues = items;
        this.context = context;
    }


    @Override
    public Consejos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_salud, parent, false);
        return new Consejos.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.titulo.setText(mValues.get(position).getNombre());
        holder.descripcion.setText(mValues.get(position).getDetalle());

    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView titulo;
        final TextView descripcion;

        public ViewHolder(View view) {
            super(view);
            titulo = view.findViewById(R.id.titulo);
            descripcion = view.findViewById(R.id.descripcion);

        }
    }
}
