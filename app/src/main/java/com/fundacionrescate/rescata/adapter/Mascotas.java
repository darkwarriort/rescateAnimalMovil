package com.fundacionrescate.rescata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.model.Adopcion;
import com.fundacionrescate.rescata.model.ObAdopcion;
import com.fundacionrescate.rescata.model.Reporte;
import com.squareup.picasso.Callback;

import java.util.ArrayList;

public class Mascotas extends RecyclerView.Adapter<Mascotas.ViewHolder> implements Filterable{

    private final ArrayList<ObAdopcion> mValues;
    private  ArrayList<ObAdopcion> mValuesFilter;
    private  ArrayList<ObAdopcion> mValuesFilterTMP;

    Context context;
    private CustomFilter mFilter;

    public Mascotas( ArrayList<ObAdopcion> items, Context context) {
        mValues = items;
        mValuesFilter = new ArrayList<>();
        mValuesFilter.addAll(mValues);
        mValuesFilterTMP = new ArrayList<>();

        this.context = context;
        this.mFilter = new CustomFilter(Mascotas.this);

    }


    public  ArrayList<ObAdopcion> getmValuesFilter()
    {
        return mValuesFilter;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mascota, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nombre.setText(/*"Nombre: \t"+*/mValuesFilter.get(position).getNombre());

        holder.raza.setText(/*"Raza: \t"+*/mValuesFilter.get(position).getRaza());
        holder.sexo.setText(/*"Sexo: \t"+*/mValuesFilter.get(position).getSexo());
        holder.especie.setText(/*"Especie: \t"+*/mValuesFilter.get(position).getEspecie());
        holder.descripcion.setText(mValuesFilter.get(position).getDescripcion());
        holder.edad.setText(/*"Edad: \t"+*/mValuesFilter.get(position).getEdad()+" años");
        holder.checkBox.setChecked(mValuesFilter.get(position).isCheck());
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.checkBox.getTag();

                if (mValuesFilter.get(pos).isCheck()) {
                    mValuesFilter.get(pos).setCheck(false);
                } else {
                    for (int i = 0; i < mValuesFilter.size(); i++)
                    {
                        mValuesFilter.get(i).setCheck(false);
                    }
                    mValuesFilter.get(pos).setCheck(true);

                }
                notifyDataSetChanged();
            }
        });
//        holder.checkBox.setOnClickListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b){
//
//                }
//
//                mValuesFilter.get(position).setCheck(b);
//            }
//        });
        System.out.println("FOTO : " +AppConfig.HOST_IMAGE+mValuesFilter.get(position).getFoto());

        Glide.with(context)
                .load(AppConfig.HOST_IMAGE+mValuesFilter.get(position).getFoto())
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



    /*Filtro*/
    public class CustomFilter extends Filter {
        private Mascotas listAdapter;

        private CustomFilter(Mascotas listAdapter) {
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
                for (final ObAdopcion person : mValues) {
//                    if (person.getName().toLowerCase().contains(filterPattern)) {
//                        mValuesFilter.add(person);
//                    }
                }
            }
            results.values = mValuesFilter;
            results.count = mValuesFilter.size();
            return results;
        }

        public void filter(String raza,String especie, String idSexo){
//            mValuesFilter.clear();
//            final FilterResults results = new FilterResults();
//            mValuesFilter.addAll(mValues);

//            if(!idSexo.isEmpty()){
//                for (final ObAdopcion adopcion : mValues) {
//                    if (!adopcion.getSexo().equals( idSexo)){
//                        mValuesFilter.remove(adopcion);
//                    }
//                }
//            }
//            if(!raza.isEmpty()){
//                for (final ObAdopcion adopcion : mValuesFilter) {
//                    if (!adopcion.getRaza().equals( raza)){
//                        mValuesFilter.remove(adopcion);
//                    }
//                }
//            }else
//            if(!especie.isEmpty()){
//                for (final ObAdopcion adopcion : mValuesFilter) {
//                    if (!adopcion.getEspecie().equals( especie)){
//                        mValuesFilter.remove(adopcion);
//                    }
//                }
//            }
//
            mValuesFilter.clear();
            mValuesFilterTMP.clear();

            if(!idSexo.isEmpty()){
                for ( final ObAdopcion adopcion : mValues) {

                    if(adopcion.getSexo()!= null){
                        if (adopcion.getSexo().equals( idSexo)){
                            mValuesFilterTMP.add(adopcion);
                        }
                    }
                }
            }else{
                mValuesFilterTMP.addAll(mValues);
            }


            if(!raza.isEmpty()){

                for ( final ObAdopcion adopcion : mValuesFilterTMP) {
                    if (adopcion.getRaza().equals( raza)){
                        mValuesFilter.add(adopcion);
                    }
                }
            }else
            if(!especie.isEmpty()){
                for ( final ObAdopcion adopcion : mValuesFilterTMP) {
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
    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgMascota;

        final TextView nombre;
        final TextView especie;
        final TextView sexo;
        final TextView edad;
        final CheckBox checkBox;
        final TextView descripcion;
        final TextView raza;

        public ViewHolder(View view) {
            super(view);
            imgMascota = view.findViewById(R.id.imgMascota);
            nombre = view.findViewById(R.id.nombre);
            checkBox = view.findViewById(R.id.checkbox);
            especie = view.findViewById(R.id.especie);
            raza = view.findViewById(R.id.raza);
            sexo = view.findViewById(R.id.sexo);
            edad = view.findViewById(R.id.edad);
            descripcion = view.findViewById(R.id.descripcion);

        }
    }
}
