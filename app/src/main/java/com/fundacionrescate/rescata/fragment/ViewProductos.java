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
import com.fundacionrescate.rescata.model.Mascota;
import com.fundacionrescate.rescata.model.Producto;

import java.util.ArrayList;
import java.util.List;

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
        items.add(new Producto());
        items.add(new Producto());
        items.add(new Producto());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_productos, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.listProducto);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        productosAdapter = new Productos(items);
        recyclerView.setAdapter(productosAdapter);
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
}
