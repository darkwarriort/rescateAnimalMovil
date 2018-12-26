package com.fundacionrescate.rescata.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.fundacionrescate.rescata.adapter.MascotasReportadas;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.maps.ReportsList;
import com.fundacionrescate.rescata.model.Especie;
import com.fundacionrescate.rescata.model.ObAdopcion;
import com.fundacionrescate.rescata.model.Raza;
import com.fundacionrescate.rescata.model.Reporte;
import com.fundacionrescate.rescata.model.Sexo;
import com.fundacionrescate.rescata.model.Usuario;
import com.fundacionrescate.rescata.util.OnCustomItemSelectedListener;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fundacionrescate.rescata.app.AppConfig.URL_ADOPCIONES;
import static com.fundacionrescate.rescata.app.AppConfig.URL_LIST_REPORTE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewMascotasReportadas extends Fragment {

    Context context ;

    SharedPreferences prefs;

    RecyclerView recyclerView;
    ArrayList<Reporte> items;
    ArrayList<Reporte> itemsTo;

    MascotasReportadas mascotasAdapter;




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


    public ViewMascotasReportadas() {
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
        View v = inflater.inflate(R.layout.fragment_view_mascotas_reportadas, container, false);
        ButterKnife.bind(this, v);

        recyclerView = (RecyclerView) v.findViewById(R.id.listProducto);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        mascotasAdapter = new MascotasReportadas(items,context);
//        recyclerView.setAdapter(mascotasAdapter);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String sUsuario = prefs.getString(AppConfig.PREF_USUARIO,null);
        Usuario userRegistrado = new Gson().fromJson(sUsuario, Usuario.class);
        Consulta.GETARRAY(URL_LIST_REPORTE+"/"+userRegistrado.getId_usuario(),consultaAdopcion);
        return v;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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




    Consulta.CallBackConsulta consultaAdopcion = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {
                items.clear();
                items.addAll(Arrays.asList(new Gson().fromJson(response.toString(), Reporte[].class)));


                mascotasAdapter = new MascotasReportadas(items,context);
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


    /*
    *
    *
    *
    * */


    OnCustomItemSelectedListener listenerSpinnerEspecie = new OnCustomItemSelectedListener() {
        @Override
        protected void onItemSelected(String string) {
//            ((MascotasReportadas.CustomFilter)mascotasAdapter.getFilter()).filter("",string,spnSexo.getText().toString());
            long idSexo = 0;
            for (Sexo e : lstSexo){
                if(e.getNombre().equals(spnSexo.getText().toString())){
                    idSexo = e.getId_sexo();
                    break;
                }
            }
            ((MascotasReportadas.CustomFilter) mascotasAdapter.getFilter()).filter(spnRaza.getText().toString(), spnEspecie.getText().toString(), idSexo);

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
            for (Sexo e : lstSexo){
                if(e.getNombre().equals(string)){

                    ((MascotasReportadas.CustomFilter) mascotasAdapter.getFilter()).filter(spnRaza.getText().toString(), spnEspecie.getText().toString(), e.getId_sexo());
                    break;
                }
            }
        }
    };
    OnCustomItemSelectedListener listenerSpinnerRaza= new OnCustomItemSelectedListener() {
        @Override
        protected void onItemSelected(String string) {
            long idSexo = 0;
            for (Sexo e : lstSexo){
                if(e.getNombre().equals(spnSexo.getText().toString())){
                    idSexo = e.getId_sexo();
                    break;
                }
            }
            ((MascotasReportadas.CustomFilter) mascotasAdapter.getFilter()).filter(spnRaza.getText().toString(), spnEspecie.getText().toString(), idSexo);

//            ((MascotasReportadas.CustomFilter) mascotasAdapter.getFilter()).filter(spnRaza.getText().toString(), spnEspecie.getText().toString(), spnSexo.getText().toString());
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









}
