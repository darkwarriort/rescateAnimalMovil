package com.fundacionrescate.rescata.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.fragment.Registro;
import com.fundacionrescate.rescata.model.Consejo;
import com.fundacionrescate.rescata.model.Evento;
import com.fundacionrescate.rescata.model.Participante;
import com.fundacionrescate.rescata.model.Usuario;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Eventos extends RecyclerView.Adapter<Eventos.ViewHolder>{

    private final ArrayList<Evento> mValues;
    Context context;
    SharedPreferences prefs;
    boolean isLoggeado;
    Usuario userRegistrado= null;


    public Eventos( ArrayList<Evento> items, Context context) {
        mValues = items;
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        isLoggeado = prefs.getBoolean(AppConfig.PREF_isLOGGED, false);
        if(isLoggeado){
            String sUsuario = prefs.getString(AppConfig.PREF_USUARIO,null);
            userRegistrado = new Gson().fromJson(sUsuario, Usuario.class);
        }
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

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isLoggeado){

                    if(userRegistrado.getId_grupo()!= null && userRegistrado.getId_grupo().compareTo(Long.parseLong("3"))==0){

                        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setTitle(context.getString(R.string.app_name))
                                .setMessage("Deseas registrarse para el evento")
                                .setCancelable(false)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                        try {
                                            Participante participante = new Participante();
                                            participante.setId_evento(mValues.get(position).getIdEventos());
                                            participante.setId_usuario(userRegistrado.getId_usuario());
                                            Consulta.POST(new JSONObject(new Gson().toJson(participante)), AppConfig.URL_EVENTO_PARTICIPANTE,postAgregar);

                                        }catch (Exception e){

                                        }

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                                    }
                                });
                        dialog.show();
                    }

                }else{
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle(context.getString(R.string.app_name))
                            .setMessage("Deseas registrarse en la app Cola")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_content, new Registro());
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                                }
                            });
                    dialog.show();

                }
            }
        });


    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }



    Consulta.CallBackConsulta postAgregar = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            System.out.println(response);
                final Participante participante = new Gson().fromJson(response.toString(),Participante.class);

                if(participante.getId_part() != null && participante.getId_part()>0)
                {
                    Toast.makeText(context,"Registrado con exito",Toast.LENGTH_SHORT).show();
                }
        }

        @Override
        public Context getContext() {
            return context;
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView titulo;
        final TextView descripcion;
        final TextView fecha;
        final TextView lugar;
        final LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            titulo = view.findViewById(R.id.titulo);
            descripcion = view.findViewById(R.id.descripcion);
            fecha = view.findViewById(R.id.fecha);
            lugar = view.findViewById(R.id.lugar);
            layout = view.findViewById(R.id.layout);

        }
    }
}
