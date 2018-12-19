package com.fundacionrescate.rescata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.model.Consejo;
import com.fundacionrescate.rescata.model.Evento;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Eventos extends RecyclerView.Adapter<Eventos.ViewHolder>{

    private final ArrayList<Evento> mValues;
    Context context;
    public Eventos( ArrayList<Evento> items, Context context) {
        mValues = items;
        this.context = context;
    }


    @Override
    public Eventos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_evento, parent, false);
        return new Eventos.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final Eventos.ViewHolder holder, final int position) {
        holder.titulo.setText(mValues.get(position).getActividad());
        holder.descripcion.setText(mValues.get(position).getDescripcion());
        holder.lugar.setText("Lugar: "+mValues.get(position).getLugar());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //to convert Date to String, use format method of SimpleDateFormat class.
        String strDate = dateFormat.format(mValues.get(position).getFecha());
        holder.fecha.setText(strDate);


    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView titulo;
        final TextView descripcion;
        final TextView fecha;
        final TextView lugar;

        public ViewHolder(View view) {
            super(view);
            titulo = view.findViewById(R.id.titulo);
            descripcion = view.findViewById(R.id.descripcion);
            fecha = view.findViewById(R.id.fecha);
            lugar = view.findViewById(R.id.lugar);

        }
    }
}
