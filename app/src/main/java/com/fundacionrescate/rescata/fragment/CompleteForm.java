package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.maps.ReportsList;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteForm extends Fragment {
    @BindView(R.id.spnSpecies)

    MaterialBetterSpinner spnEspecie;
    @BindView(R.id.spnReporte)
    MaterialBetterSpinner spnReporte;
    Context context;

    String[] SPINNERREPORTE = {"Abandono","Extraviado"};
    String[] SPINNERESPECIE = {"Perro","Gato"};
    public CompleteForm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_complete_form, container, false);
        ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, SPINNERESPECIE);
        spnEspecie.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, SPINNERREPORTE);
        spnReporte.setAdapter(arrayAdapter2);
    }

    @OnClick(R.id.complete_form_salir)
    void exitForm() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        fragmentTransaction.replace(R.id.fragment_content, new ReportsList());
        fragmentTransaction.commit();
    }

    @OnClick(R.id.complete_form_guardar)
    void saveForm() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        fragmentTransaction.replace(R.id.fragment_content, new ReportsList());
        fragmentTransaction.commit();
    }

}
