package com.fundacionrescate.rescata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.model.Adopcion;
import com.fundacionrescate.rescata.model.ObAdopcion;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Mascotas extends RecyclerView.Adapter<Mascotas.ViewHolder> {

    private final ArrayList<ObAdopcion> mValues;
    Context context;
    public Mascotas( ArrayList<ObAdopcion> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mascota, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.nombre.setText(mValues.get(position).getNombre());
        holder.raza.setText("Raza: \t"+mValues.get(position).getRaza());
        holder.sexo.setText("Sexo: \t"+mValues.get(position).getSexo());
        holder.especie.setText("Especie \t"+mValues.get(position).getEspecie());
        System.out.println("FOTO : " +AppConfig.HOST_IMAGE+mValues.get(position).getFoto());
        Picasso.with(context).load(AppConfig.HOST_IMAGE+mValues.get(position).getFoto())
                .placeholder(R.drawable.ic_pawprint)
                .error(R.drawable.ic_pawprint)
                .centerInside()
                .fit()
                .into(holder.imgMascota, new Callback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("Success LOAD");

                    }

                    @Override
                    public void onError() {
                        System.out.println("ERROR LOAD");
                    }
                });

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
        final TextView edad;
        final TextView raza;

        public ViewHolder(View view) {
            super(view);
            imgMascota = view.findViewById(R.id.imgMascota);
            nombre = view.findViewById(R.id.nombre);
            especie = view.findViewById(R.id.especie);
            raza = view.findViewById(R.id.raza);
            sexo = view.findViewById(R.id.sexo);
            edad = view.findViewById(R.id.edad);

        }
    }
}
