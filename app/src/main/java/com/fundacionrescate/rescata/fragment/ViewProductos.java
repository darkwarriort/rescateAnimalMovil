package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.adapter.MascotasReportadas;
import com.fundacionrescate.rescata.adapter.Productos;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Especie;
import com.fundacionrescate.rescata.model.Mascota;
import com.fundacionrescate.rescata.model.Producto;
import com.fundacionrescate.rescata.model.Sexo;
import com.fundacionrescate.rescata.util.OnCustomItemSelectedListener;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.fundacionrescate.rescata.app.AppConfig.URL_ADOPCIONES;
import static com.fundacionrescate.rescata.app.AppConfig.URL_PRODUCTOS;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProductos extends Fragment {

    Context context ;

    @BindView(R.id.spnProductos)
    MaterialBetterSpinner spnProductos;


    RecyclerView recyclerView;
    List<Producto> items;
    Productos productosAdapter;



    ArrayAdapter<String> adapterCategoria;
    String[] lstCategoria;

    public ViewProductos() {
        // Required empty public constructor
        items = new ArrayList<>();
        lstCategoria = new String[4];


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_productos, container, false);
        ButterKnife.bind(this,v);
        recyclerView = (RecyclerView) v.findViewById(R.id.listProducto);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        productosAdapter = new Productos(items,context);
        recyclerView.setAdapter(productosAdapter);
        lstCategoria[0]="Todos";
        lstCategoria[1]="Mascotas";
        lstCategoria[2]="Uso Personal";
        lstCategoria[3]="Varios";

        return v ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterCategoria= new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstCategoria);
        spnProductos.setAdapter(adapterCategoria);
        spnProductos.setText("Todos");

        spnProductos.addTextChangedListener(listenerSpinnerEspecie );
        Consulta.GETARRAY(URL_PRODUCTOS,consultaProductos);

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
                ((Productos.CustomFilter) productosAdapter.getFilter()).filter(spnProductos.getText().toString());
                productosAdapter.notifyDataSetChanged();
//                productosAdapter.notifyDataSetChanged();
//                ((Productos.CustomFilter) productosAdapter.getFilter()).filter("Todos");

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public Context getContext() {
            return context;
        }
    };



    OnCustomItemSelectedListener listenerSpinnerEspecie = new OnCustomItemSelectedListener() {
        @Override
        protected void onItemSelected(String string) {
//            ((MascotasReportadas.CustomFilter)mascotasAdapter.getFilter()).filter("",string,spnSexo.getText().toString());
            Consulta.GETARRAY(URL_PRODUCTOS,consultaProductos);
//            ((Productos.CustomFilter) productosAdapter.getFilter()).filter(spnProductos.getText().toString());
//            productosAdapter.notifyDataSetChanged();

       }
    };
}
