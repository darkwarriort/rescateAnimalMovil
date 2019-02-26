package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.adapter.Consejos;
import com.fundacionrescate.rescata.adapter.Donaciones;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Consejo;
import com.fundacionrescate.rescata.model.Donacion;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;

import static com.fundacionrescate.rescata.app.AppConfig.URL_DONACION;
import static com.fundacionrescate.rescata.app.AppConfig.URL_SALUD;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDonaciones extends Fragment {

    Context context;
    ArrayList<Donacion> items;


    SharedPreferences prefs;

    RecyclerView recyclerView;
    Donaciones donacionesAdapter;
    public ViewDonaciones() {
        // Required empty public constructor
        items = new ArrayList<>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_donaciones, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle("Donaciones");

        recyclerView = (RecyclerView) v.findViewById(R.id.listDonacion);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        donacionesAdapter = new Donaciones(items,context);
        recyclerView.setAdapter(donacionesAdapter);
        Consulta.GETARRAY(URL_DONACION,consultaDonaciones);
        return v;
    }


    Consulta.CallBackConsulta consultaDonaciones = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {
                items.clear();
                items.addAll(Arrays.asList(new Gson().fromJson(response.toString(), Donacion[].class)));
                donacionesAdapter.notifyDataSetChanged();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public Context getContext() {
            return context;
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
