package com.fundacionrescate.rescata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.model.Producto;

import java.util.List;

public class Productos extends RecyclerView.Adapter<Productos.ViewHolder> {

    private final List<Producto> mValues;
    Context context;
    public Productos(List<Producto> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.descripcion.setText(mValues.get(position).getDescripcion());
        holder.nombre.setText(mValues.get(position).getNombre());
        System.out.println("FOTO : " +AppConfig.HOST_IMAGE+mValues.get(position).getFoto());

        Glide.with(context)
                .load(AppConfig.HOST_IMAGE+mValues.get(position).getFoto())
                .error(R.drawable.ic_pawprint)
                .into(holder.imgProducto);
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgProducto;

        final TextView nombre;
        final TextView descripcion;

        public ViewHolder(View view) {
            super(view);
            imgProducto = view.findViewById(R.id.imgProducto);
            nombre = view.findViewById(R.id.nombre);
            descripcion = view.findViewById(R.id.descripcion);

        }
    }
}
