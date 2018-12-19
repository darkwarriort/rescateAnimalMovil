package com.fundacionrescate.rescata.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionrescate.rescata.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Servicios extends Fragment {


    public Servicios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_servicios, container, false);
        ButterKnife.bind(this,v);
        getActivity().setTitle("Servicios");

        return v;
    }
    @OnClick(R.id.btnSalud)
    void clickSalud(){


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content,new Salud());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @OnClick(R.id.btnDonaciones)
    void clickDonacion(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content,new Donaciones());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    @OnClick(R.id.btnEvento)
    void clickEvento(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content,new ViewEventos());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_content,new Eventos());
//        fragmentTransaction.commit();
    }
}
