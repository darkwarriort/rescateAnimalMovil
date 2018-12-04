package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fundacionrescate.rescata.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reporte#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reporte extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "direccion";
    private static final String ARG_PARAM2 = "coordenadas";

    // TODO: Rename and change types of parameters
    private String direccion;
    private String coordenada;
    Context context;

    @BindView(R.id.report_address_input)
    TextInputEditText report_address_input;

    @BindView(R.id.spnSpecies)

    MaterialBetterSpinner spnEspecie;
    @BindView(R.id.spnReporte)
    MaterialBetterSpinner spnReporte;


    String[] SPINNERREPORTE = {"Abandono","Extraviado"};
    String[] SPINNERESPECIE = {"Perro","Gato"};


    public Reporte() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param direccion Parameter 1.
     * @param coordenada Parameter 2.
     * @return A new instance of fragment Reporte.
     */
    // TODO: Rename and change types and number of parameters
    public static Reporte newInstance(String direccion, String coordenada) {
        Reporte fragment = new Reporte();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, direccion);
        args.putString(ARG_PARAM2, coordenada);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            direccion = getArguments().getString(ARG_PARAM1);
            coordenada = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reporte, container, false);
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
        report_address_input.setText(direccion);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, SPINNERESPECIE);
        spnEspecie.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, SPINNERREPORTE);
        spnReporte.setAdapter(arrayAdapter2);
    }


    @OnClick(R.id.register_new_button)
    void nextFormQuestion() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, new Question());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
