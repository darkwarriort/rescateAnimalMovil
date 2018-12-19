package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.adapter.Productos;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Mascota;
import com.fundacionrescate.rescata.model.Producto;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.fundacionrescate.rescata.app.AppConfig.URL_ADOPCIONES;
import static com.fundacionrescate.rescata.app.AppConfig.URL_PRODUCTOS;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProductos extends Fragment {

    Context context ;


    RecyclerView recyclerView;
    List<Producto> items;
    Productos productosAdapter;
    public ViewProductos() {
        // Required empty public constructor
        items = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_productos, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.listProducto);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        productosAdapter = new Productos(items,context);
        recyclerView.setAdapter(productosAdapter);
        Consulta.GETARRAY(URL_PRODUCTOS,consultaProductos);

        return v ;
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

    Consulta.CallBackConsulta consultaProductos = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {
                items.clear();
                items.addAll(Arrays.asList(new Gson().fromJson(response.toString(), Producto[].class)));
                productosAdapter.notifyDataSetChanged();

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
