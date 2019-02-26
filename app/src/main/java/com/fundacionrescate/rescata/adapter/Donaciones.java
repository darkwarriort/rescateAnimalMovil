package com.fundacionrescate.rescata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.model.Donacion;

import java.util.ArrayList;

public class Donaciones  extends RecyclerView.Adapter<Donaciones.ViewHolder>{

    private final ArrayList<Donacion> mValues;
    Context context;
    public Donaciones( ArrayList<Donacion> items, Context context) {
        mValues = items;
        this.context = context;
    }


    @Override
    public Donaciones.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donacion, parent, false);
        return new Donaciones.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.categoria.setText(mValues.get(position).getCategoria());
        holder.detalle.setText(mValues.get(position).getDetalle());

    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView categoria;
        final TextView detalle;
//        final TextView tipo;

        public ViewHolder(View view) {
            super(view);
            categoria = view.findViewById(R.id.categoria);
            detalle = view.findViewById(R.id.detalle);
//            tipo = view.findViewById(R.id.tipo);

        }
    }
}
