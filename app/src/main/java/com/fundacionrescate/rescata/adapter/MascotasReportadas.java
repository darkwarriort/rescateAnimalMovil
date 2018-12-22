package com.fundacionrescate.rescata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.model.Reporte;

import java.util.ArrayList;

public class MascotasReportadas extends RecyclerView.Adapter<MascotasReportadas.ViewHolder> {

    private final ArrayList<Reporte> mValues;
    Context context;
    public MascotasReportadas(ArrayList<Reporte> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mascota_reportada, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(mValues.get(position).getNombre()!= null){
            holder.nombre.setText("Nombre: \t"+mValues.get(position).getNombre());
            holder.nombre.setVisibility(View.VISIBLE);

        }else{
            holder.nombre.setVisibility(View.INVISIBLE);
        }
        holder.raza.setText("Raza: \t"+mValues.get(position).getRaza());
        holder.especie.setText("Especie: \t"+mValues.get(position).getEspecie());
        holder.sexo.setText("Direccion: \t"+mValues.get(position).getDireccion());



        System.out.println("FOTO : " +AppConfig.HOST_UPLOAD+mValues.get(position).getFoto());

        Glide.with(context)
                .load(AppConfig.HOST_UPLOAD+mValues.get(position).getFoto())
                .error(R.drawable.ic_pawprint)
                .into(holder.imgMascota);


    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgMascota;

        final TextView nombre;
        final TextView especie;
        final TextView sexo;
        final TextView raza;

        public ViewHolder(View view) {
            super(view);
            imgMascota = view.findViewById(R.id.imgMascota);
            nombre = view.findViewById(R.id.nombre);
            especie = view.findViewById(R.id.especie);
            raza = view.findViewById(R.id.raza);
            sexo = view.findViewById(R.id.sexo);
        }
    }
}
