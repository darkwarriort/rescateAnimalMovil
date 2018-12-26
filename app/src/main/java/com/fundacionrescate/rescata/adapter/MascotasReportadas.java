package com.fundacionrescate.rescata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.model.ObAdopcion;
import com.fundacionrescate.rescata.model.Reporte;

import java.util.ArrayList;

public class MascotasReportadas extends RecyclerView.Adapter<MascotasReportadas.ViewHolder> implements Filterable {

    private final ArrayList<Reporte> mValues;

    private  ArrayList<Reporte> mValuesFilter;
    private  ArrayList<Reporte> mValuesFilterTMP;

    Context context;
    private MascotasReportadas.CustomFilter mFilter;


    public MascotasReportadas(ArrayList<Reporte> items, Context context) {
        mValues = items;
        this.context = context;
        mValuesFilter = new ArrayList<>();
        mValuesFilter.addAll(mValues);
        mValuesFilterTMP = new ArrayList<>();
        this.mFilter = new MascotasReportadas.CustomFilter(MascotasReportadas.this);

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
            holder.nombre.setText("Nombre: \t"+mValuesFilter.get(position).getNombre());
            holder.nombre.setVisibility(View.VISIBLE);

        }else{
            holder.nombre.setVisibility(View.INVISIBLE);
        }
        holder.raza.setText("Raza: \t"+mValuesFilter.get(position).getRaza());
        holder.especie.setText("Especie: \t"+mValuesFilter.get(position).getEspecie());
        holder.sexo.setText("Direccion: \t"+mValuesFilter.get(position).getDireccion());



        System.out.println("FOTO : " +AppConfig.HOST_UPLOAD+ mValuesFilter.get(position).getFoto());

        Glide.with(context)
                .load(AppConfig.HOST_UPLOAD+mValuesFilter.get(position).getFoto())
                .error(R.drawable.ic_pawprint)
                .into(holder.imgMascota);


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




    /*Filtro*/
    public class CustomFilter extends Filter {
        private MascotasReportadas listAdapter;

        private CustomFilter(MascotasReportadas listAdapter) {
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
                for (final Reporte person : mValues) {
//                    if (person.getName().toLowerCase().contains(filterPattern)) {
//                        mValuesFilter.add(person);
//                    }
                }
            }
            results.values = mValuesFilter;
            results.count = mValuesFilter.size();
            return results;
        }

        public void filter(String raza,String especie, long idSexo){
            mValuesFilter.clear();
            mValuesFilterTMP.clear();

            if(idSexo>0){
                for ( final Reporte adopcion : mValues) {

                    if(adopcion.getId_sexo()!= null){
                        if (adopcion.getId_sexo()== idSexo){
                            mValuesFilterTMP.add(adopcion);
                        }
                    }
                }
            }else{
                mValuesFilterTMP.addAll(mValues);
            }

            if(!raza.isEmpty()){

                for ( final Reporte adopcion : mValuesFilterTMP) {
                    if (adopcion.getRaza().equals( raza)){
                        mValuesFilter.add(adopcion);
                    }
                }
            }else
            if(!especie.isEmpty()){
                for ( final Reporte adopcion : mValuesFilterTMP) {
                    if (adopcion.getEspecie().equals( especie)){
                        mValuesFilter.add(adopcion);
                    }
                }
            }else{
                mValuesFilter.addAll(mValuesFilterTMP);
            }


            notifyDataSetChanged();

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
