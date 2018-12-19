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
import com.fundacionrescate.rescata.adapter.Eventos;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Consejo;
import com.fundacionrescate.rescata.model.Evento;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;

import static com.fundacionrescate.rescata.app.AppConfig.URL_EVENTO;
import static com.fundacionrescate.rescata.app.AppConfig.URL_SALUD;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewEventos extends Fragment {

    Context context;
    ArrayList<Evento> items;


    SharedPreferences prefs;

    RecyclerView recyclerView;

    ArrayList<Evento> itemsTo;

    Eventos eventosAdapter;
    public ViewEventos() {
        // Required empty public constructor
        items = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_eventos, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle("Eventos");
        recyclerView = (RecyclerView) v.findViewById(R.id.listProducto);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        eventosAdapter = new Eventos(items,context);
        recyclerView.setAdapter(eventosAdapter);
        Consulta.GETARRAY(URL_EVENTO,consultaEventos);
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

    Consulta.CallBackConsulta consultaEventos = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {
                items.clear();
                items.addAll(Arrays.asList(new Gson().fromJson(response.toString(), Evento[].class)));
                eventosAdapter.notifyDataSetChanged();
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
