package com.fundacionrescate.rescata.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.adapter.Mascotas;
import com.fundacionrescate.rescata.maps.ReportsList;
import com.fundacionrescate.rescata.model.Mascota;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewMascotas extends Fragment {

    Context context ;


    RecyclerView recyclerView;
    List<Mascota> items;
    Mascotas mascotasAdapter;
    public ViewMascotas() {
        // Required empty public constructor
        items = new ArrayList<>();
        items.add(new Mascota());
        items.add(new Mascota());
        items.add(new Mascota());
        items.add(new Mascota());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_mascotas, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.listProducto);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mascotasAdapter = new Mascotas(items);
        ButterKnife.bind(this, v);
        recyclerView.setAdapter(mascotasAdapter);
        return v;

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

    //
    @OnClick(R.id.view_mascotas_postular)
    void clickPostular() {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getString(R.string.app_name));
        alertDialog.setMessage("Gracias, nos comunicaremos con usted");
        alertDialog.setCancelable(false);
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancelar", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();


                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                            fragmentManager.popBackStack();
                        }
                        fragmentTransaction.replace(R.id.fragment_content, new ReportsList());
                        fragmentTransaction.commit();
                    }
                });
        alertDialog.show();
    }
}
