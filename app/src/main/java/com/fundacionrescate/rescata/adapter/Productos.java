package com.fundacionrescate.rescata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.model.Producto;
import com.fundacionrescate.rescata.model.Reporte;

import java.util.ArrayList;
import java.util.List;

public class Productos extends RecyclerView.Adapter<Productos.ViewHolder>   implements Filterable {

    private final List<Producto> mValues;
    Context context;

    private Productos.CustomFilter  mFilter;

    private  ArrayList<Producto> mValuesFilter;

    public Productos(List<Producto> items, Context context) {
        mValues = items;
        this.context = context;
        mValuesFilter = new ArrayList<>();
        mValuesFilter.addAll(mValues);

        this.mFilter = new Productos.CustomFilter(Productos.this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.descripcion.setText(mValuesFilter.get(position).getDescripcion());
        holder.nombre.setText(mValuesFilter.get(position).getNombre());
        System.out.println("FOTO : " +AppConfig.HOST_IMAGE+mValuesFilter.get(position).getFoto());

        Glide.with(context)
                .load(AppConfig.HOST_IMAGE+mValuesFilter.get(position).getFoto())
                .error(R.drawable.ic_pawprint)
                .into(holder.imgProducto);
    }
    @Override
    public int getItemCount() {
        return mValuesFilter.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
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

    /*############################################################################################*/
    public class CustomFilter extends Filter {
        private Productos listAdapter;

        private CustomFilter(Productos listAdapter) {
            super();
            this.listAdapter = listAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            mValuesFilter.clear();


            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                mValuesFilter.addAll(mValues);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

            }
            results.values = mValuesFilter;
            results.count = mValuesFilter.size();
            return results;
        }

        public void filter(String categoria){
            mValuesFilter.clear();
            if(categoria.equals("Todos")){
                mValuesFilter.addAll(mValues);
            }else {
                for (final Producto producto : mValues) {


                    /*if (categoria.equals("otros")) {
                        if (producto.getCategoria() == null) {
                            mValuesFilter.add(producto);

                        }
                    } else*/
                    if (producto.getCategoria() != null) {
                        if (producto.getCategoria().equals(categoria)) {
                            mValuesFilter.add(producto);
                        }
                    } else {
                        mValuesFilter.addAll(mValues);
                    }
                }
            }


            notifyDataSetChanged();

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.listAdapter.notifyDataSetChanged();
        }
    }


}
