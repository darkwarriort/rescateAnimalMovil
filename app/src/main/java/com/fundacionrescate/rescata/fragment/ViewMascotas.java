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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.adapter.Mascotas;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.maps.ReportsList;
import com.fundacionrescate.rescata.model.Adopcion;
import com.fundacionrescate.rescata.model.Especie;
import com.fundacionrescate.rescata.model.Mascota;
import com.fundacionrescate.rescata.model.ObAdopcion;
import com.fundacionrescate.rescata.model.Raza;
import com.fundacionrescate.rescata.model.Sexo;
import com.fundacionrescate.rescata.util.OnCustomItemSelectedListener;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

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

    SharedPreferences prefs;

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
        for(ObAdopcion obAdopcion:mascotasAdapter.getmValuesFilter()){
            if(obAdopcion.isCheck()){
                itemsTo.add(obAdopcion);
            }
        }
        System.out.println("JSON POSTULA: "+itemsTo.toString());
        boolean isLoggeado = prefs.getBoolean(AppConfig.PREF_isLOGGED, false);
        if(isLoggeado) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(context.getString(R.string.app_name));
            alertDialog.setMessage("Gracias, nos comunicaremos con usted");
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();


                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                                fragmentManager.popBackStack();
                            }
                            fragmentTransaction.replace(R.id.fragment_content, new ReportsList());
                            fragmentTransaction.commit();
                        }
                    });
            alertDialog.show();
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
