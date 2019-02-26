package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.adapter.Consejos;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Consejo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;

import static com.fundacionrescate.rescata.app.AppConfig.URL_PRODUCTOS;
import static com.fundacionrescate.rescata.app.AppConfig.URL_SALUD;

/**
 * A simple {@link Fragment} subclass.
 */
public class Salud extends Fragment {

    Context context;
    ArrayList<Consejo> items;


    SharedPreferences prefs;

    RecyclerView recyclerView;

    ArrayList<Consejo> itemsTo;

    Consejos consejosAdapter;
    public Salud() {
        // Required empty public constructor
        items = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_salud, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle("Consejos de Salud");

        recyclerView = (RecyclerView) v.findViewById(R.id.listProducto);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        consejosAdapter = new Consejos(items,context);
        recyclerView.setAdapter(consejosAdapter);

        Consulta.GETARRAY(URL_SALUD,consultaProductos);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    Consulta.CallBackConsulta consultaProductos = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {
                items.clear();
                items.addAll(Arrays.asList(new Gson().fromJson(response.toString(), Consejo[].class)));
                System.out.println("Size salud "+ items.size());
                consejosAdapter.notifyDataSetChanged();

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
