package com.fundacionrescate.rescata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.model.Consejo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Consejos extends RecyclerView.Adapter<Consejos.ViewHolder>{

    private final ArrayList<Consejo> mValues;
    Context context;
    public Consejos( ArrayList<Consejo> items, Context context) {
        mValues = items;
        System.out.println("VALUES Consejo items "+ mValues.size());

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
        System.out.println("VALUES POSICION "+ position);
        holder.titulo.setText(mValues.get(position).getNombre());
        holder.descripcion.setText(mValues.get(position).getDetalle());

        Date date=new Date(mValues.get(position).getFecha_ingreso());
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String sFecha = df2.format(date);
        holder.fecha.setText(sFecha);

    }
    @Override
    public int getItemCount() {
        System.out.println("VALUES Consejo items "+ mValues.size());

        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView titulo;
        final TextView descripcion;
        final TextView fecha;

        public ViewHolder(View view) {
            super(view);
            titulo = view.findViewById(R.id.titulo);
            descripcion = view.findViewById(R.id.descripcion);
            fecha = view.findViewById(R.id.fecha);

        }
    }
}
