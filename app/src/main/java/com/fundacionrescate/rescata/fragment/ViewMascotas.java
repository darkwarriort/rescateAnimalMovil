package com.fundacionrescate.rescata.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.adapter.Mascotas;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.maps.ReportsList;
import com.fundacionrescate.rescata.model.Adopcion;
import com.fundacionrescate.rescata.model.Especie;
import com.fundacionrescate.rescata.model.Mascota;
import com.fundacionrescate.rescata.model.ObAdopcion;
import com.fundacionrescate.rescata.model.Postulantes;
import com.fundacionrescate.rescata.model.Raza;
import com.fundacionrescate.rescata.model.Sexo;
import com.fundacionrescate.rescata.model.Usuario;
import com.fundacionrescate.rescata.util.OnCustomItemSelectedListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fundacionrescate.rescata.app.AppConfig.URL_ADOPCIONES;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewMascotas extends Fragment {

    Context context ;
    boolean isLoggeado;


    SharedPreferences prefs;
    Usuario userRegistrado= null;
    RecyclerView recyclerView;
    ArrayList<ObAdopcion> items;
    ArrayList<ObAdopcion> itemsTo;

    Mascotas mascotasAdapter;

    @BindView(R.id.spnEspecie)
    MaterialBetterSpinner spnEspecie;
    @BindView(R.id.spnSexo)
    MaterialBetterSpinner spnSexo;
    @BindView(R.id.spnRaza)
    MaterialBetterSpinner spnRaza;

    ArrayAdapter<Especie> adapterEspecie;
    ArrayAdapter<Raza> adapterRaza;
    Especie[] lstEspecie;
    Raza[] lstRaza;

    ArrayAdapter<Sexo> adapterSexo;
    Sexo[] lstSexo;

    public ViewMascotas() {
        // Required empty public constructor
        items = new ArrayList<>();
        lstEspecie = new Especie[0];
        lstRaza  = new Raza[0];
        lstSexo = new Sexo[0];

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_mascotas, container, false);
        ButterKnife.bind(this, v);

        recyclerView = (RecyclerView) v.findViewById(R.id.listProducto);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mascotasAdapter = new Mascotas(items,context);
        recyclerView.setAdapter(mascotasAdapter);

        Consulta.GETARRAY(URL_ADOPCIONES,consultaAdopcion);
        return v;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        adapterEspecie= new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstEspecie);
        spnEspecie.setAdapter(adapterEspecie);

        adapterRaza = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstRaza);
        spnRaza.setAdapter(adapterRaza);
        spnRaza.addTextChangedListener(listenerSpinnerRaza);

        Consulta.GETARRAY(AppConfig.URL_ESPECIE, consultaEspecie);

        spnEspecie.addTextChangedListener(listenerSpinnerEspecie );
        adapterSexo= new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstSexo);
        spnSexo.setAdapter(adapterSexo);
        spnSexo.addTextChangedListener(listenerSpinnerSexo);
        Consulta.GETARRAY(AppConfig.URL_SEXO, consultaSexo);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

    }
    @Override
    public void onDetach() {
        super.onDetach();

    }



    OnCustomItemSelectedListener listenerSpinnerEspecie = new OnCustomItemSelectedListener() {
        @Override
        protected void onItemSelected(String string) {
            ((Mascotas.CustomFilter)mascotasAdapter.getFilter()).filter("",string,spnSexo.getText().toString());

            for (Especie e : lstEspecie){
                if(e.getNombre().equals(string)){

                    Consulta.GETARRAY(AppConfig.URL_RAZA+"/"+ e.getIdEspecie(),consultaRaza);
                    break;
                }
            } }
    };
    OnCustomItemSelectedListener listenerSpinnerSexo = new OnCustomItemSelectedListener() {
        @Override
        protected void onItemSelected(String string) {
            ((Mascotas.CustomFilter) mascotasAdapter.getFilter()).filter(spnRaza.getText().toString(), spnEspecie.getText().toString(), spnSexo.getText().toString());
        }
    };
    OnCustomItemSelectedListener listenerSpinnerRaza= new OnCustomItemSelectedListener() {
        @Override
        protected void onItemSelected(String string) {
            spnSexo.setText("");
            ((Mascotas.CustomFilter) mascotasAdapter.getFilter()).filter(spnRaza.getText().toString(), spnEspecie.getText().toString(), spnSexo.getText().toString());
        }
    };

    Consulta.CallBackConsulta consultaSexo = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {

                lstSexo = new Gson().fromJson(response.toString(), Sexo[].class);
                adapterSexo = new ArrayAdapter<>(context,
                        android.R.layout.simple_dropdown_item_1line, lstSexo);
                spnSexo.setAdapter(adapterSexo);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public Context getContext() {
            return context;
        }
    };
    Consulta.CallBackConsulta consultaRaza = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            lstRaza = new Gson().fromJson(response.toString(), Raza[].class);

            adapterRaza = new ArrayAdapter<>(context,
                    android.R.layout.simple_dropdown_item_1line, lstRaza);
            spnRaza.setAdapter(adapterRaza);
            spnRaza.setText("");
            spnSexo.setText("");
        }

        @Override
        public Context getContext() {
            return context;
        }
    };

    Consulta.CallBackConsulta consultaEspecie = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {

                lstEspecie = new Gson().fromJson(response.toString(), Especie[].class);
                adapterEspecie = new ArrayAdapter<>(context,
                        android.R.layout.simple_dropdown_item_1line, lstEspecie);
                spnEspecie.setAdapter(adapterEspecie);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public Context getContext() {
            return context;
        }
    };
    //
    @OnClick(R.id.view_mascotas_postular)
    void clickPostular() {
        itemsTo = new ArrayList<>();
        ObAdopcion obAdopcion = null;

        for(ObAdopcion obAdopcion1:mascotasAdapter.getmValuesFilter()){
            if(obAdopcion1.isCheck()){
                obAdopcion = obAdopcion1;

            }
        }
        System.out.println("JSON POSTULA: "+itemsTo.toString());
        isLoggeado = prefs.getBoolean(AppConfig.PREF_isLOGGED, false);
        String sUsuario = prefs.getString(AppConfig.PREF_USUARIO,null);
        userRegistrado = new Gson().fromJson(sUsuario, Usuario.class);

        if(isLoggeado) {
//            List<Postulantes> postulantes = new ArrayList<>();
//            for(ObAdopcion obAdopcion:itemsTo){
//
//            }
            if(obAdopcion !=null) {
                Postulantes postulante = new Postulantes();
                postulante.setId_adopcion(obAdopcion.getId());
                postulante.setId_usuario(userRegistrado.getId_usuario());
//            postulantes.add(postulante);
                try {
                    Consulta.POST(new JSONObject(new Gson().toJson(postulante)), AppConfig.URL_ADOPCIONES_POSTULAR, new Consulta.CallBackConsulta() {
                        @Override
                        public void onError(Object response) {

                        }

                        @Override
                        public void onSuccess(Object response) {
                            System.out.println(response);
                            if (response != null) {
                                System.out.println("JSON POSTULA RESPONSE :" +response);

                                Postulantes res =  new Gson().fromJson(response.toString(),Postulantes.class);
                                if(res.getId_post()!=null) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                    alertDialog.setTitle(context.getString(R.string.app_name));
                                    alertDialog.setMessage("Gracias, nos comunicaremos con usted");
                                    alertDialog.setCancelable(false);
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    getActivity().finish();
                                                }
                                            });
                                    alertDialog.show();
                                }else{
                                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                    alertDialog.setTitle(context.getString(R.string.app_name));
                                    alertDialog.setMessage("Usted ya postulo por esta mascota.\n" +
                                            "Gracias.\n");
                                    alertDialog.setCancelable(false);
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();

                                                }
                                            });
                                    alertDialog.show();
                                }
                            }
                        }

                        @Override
                        public Context getContext() {
                            return context;
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(context, "No ha seleccionado ninguna mascota", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            android.support.v7.app.AlertDialog.Builder builder;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
//            } else {
            builder = new android.support.v7.app.AlertDialog.Builder(context);
//            }
            builder.setTitle("No se encuentra registrado")
                    .setMessage(getString(R.string.question))
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_content,new Registro());
//                        fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            getActivity().finish();

                        }
                    })
                    .show();

        }
    }


    Consulta.CallBackConsulta consultaAdopcion = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {
                items.clear();
                items.addAll(Arrays.asList(new Gson().fromJson(response.toString(), ObAdopcion[].class)));

                mascotasAdapter = new Mascotas(items,context);
                recyclerView.setAdapter(mascotasAdapter);
//                mascotasAdapter.notifyDataSetChanged();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public Context getContext() {
            return context;
        }
    };
}
