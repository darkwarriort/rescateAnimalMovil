package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Especie;
import com.fundacionrescate.rescata.model.Mascota;
import com.fundacionrescate.rescata.model.Raza;
import com.fundacionrescate.rescata.util.ActivityUtils;
import com.fundacionrescate.rescata.util.OnCustomItemSelectedListener;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;

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
    //    @BindView(R.id.spnReporte)
//    MaterialBetterSpinner spnReporte;
    @BindView(R.id.spnRaza)
    MaterialBetterSpinner spnRaza;


    //    String[] SPINNERREPORTE = {"Abandono","Extraviado"};
//    String[] SPINNERESPECIE = {"Perro","Gato"};
    @BindView(R.id.rdgReporte)
    RadioGroup rdgReporte;

    ArrayAdapter<Especie> adapterEspecie;
    ArrayAdapter<Raza> adapterRaza;
    //    ArrayAdapter<String> adapterReporte;
    Especie[] lstEspecie;
    Raza[] lstRaza;
    Mascota mascota = null;

    public Reporte() {
        // Required empty public constructor
        lstEspecie = new Especie[0];
        lstRaza  = new Raza[0];
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

        report_address_input.setEnabled(false);



        adapterEspecie= new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstEspecie);
        spnEspecie.setAdapter(adapterEspecie);

//        adapterReporte = new  ArrayAdapter<>(context,
//                android.R.layout.simple_dropdown_item_1line, SPINNERREPORTE);
//        spnReporte.setAdapter(adapterReporte);

        adapterRaza = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstRaza);
        spnRaza.setAdapter(adapterRaza);
        cargaDato();
        spnEspecie.addTextChangedListener(listenerSpinnerEspecie );




    }


    public void cargaDato(){
        Consulta.GETARRAY(AppConfig.URL_ESPECIE, consultaEspecie);

    }


    @OnClick(R.id.register_new_button)
    void nextFormQuestion() {
        com.fundacionrescate.rescata.model.Reporte reporte = new  com.fundacionrescate.rescata.model.Reporte();
        reporte.setEstado("ACTIVO");
        String [] gps = coordenada.split(",");

        reporte.setLongitud(Float.parseFloat(gps[0]));
        reporte.setLatitud(Float.parseFloat(gps[1]));
        reporte.setDireccion(direccion);
        reporte.setEstado_animal((String) ((RadioButton)getView().findViewById(rdgReporte.getCheckedRadioButtonId())).getText());
        if(spnRaza.getEditableText().toString().isEmpty() || spnEspecie.getEditableText().toString().isEmpty()){
            Toast.makeText(context, "Por favor complete los datos", Toast.LENGTH_SHORT).show();
        }else{
            for (Especie e : lstEspecie){
                if(e.getNombre().equals(spnEspecie.getText().toString())){
                    reporte.setId_especie(e.getIdEspecie());
                    break;
                }
            }
            for (Raza r : lstRaza){
                if(r.getNombre().equals(spnRaza.getText().toString())){
                    reporte.setId_raza(r.getId_raza());
                    break;
                }
            }
            try {

                mascota = new Mascota();
                mascota.setDireccion(direccion);
                mascota.setEspecie(spnEspecie.getText().toString());
                mascota.setRaza(spnRaza.getText().toString());
                mascota.setReporte((String) ((RadioButton)getView().findViewById(rdgReporte.getCheckedRadioButtonId())).getText());

                Consulta.POST(new JSONObject(new Gson().toJson(reporte)),AppConfig.URL_REPORTE,postAgregar);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    OnCustomItemSelectedListener listenerSpinnerEspecie = new OnCustomItemSelectedListener() {
        @Override
        protected void onItemSelected(String string) {

            for (Especie e : lstEspecie){
                if(e.getNombre().equals(string)){
                    Consulta.GETARRAY(AppConfig.URL_RAZA+"/"+ e.getIdEspecie(),consultaRaza);
                    break;
                }
            } }
    };

    Consulta.CallBackConsulta consultaEspecie = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {

                lstEspecie = new Gson().fromJson(response.toString(), Especie[].class);
                adapterEspecie = new ArrayAdapter<>(context,
                        android.R.layout.simple_dropdown_item_1line, lstEspecie);
                spnEspecie.setAdapter(adapterEspecie);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public Context getContext() {
            return context;
        }
    };

    Consulta.CallBackConsulta consultaRaza = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            lstRaza = new Gson().fromJson(response.toString(), Raza[].class);

            adapterRaza = new ArrayAdapter<>(context,
                    android.R.layout.simple_dropdown_item_1line, lstRaza);
            spnRaza.setAdapter(adapterRaza);
            spnRaza.setText("");
        }

        @Override
        public Context getContext() {
            return context;
        }
    };

    Consulta.CallBackConsulta postAgregar = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            /*
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_content, new Question());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*/


            System.out.println(response);

            final com.fundacionrescate.rescata.model.Reporte reporte = new Gson().fromJson(response.toString(), com.fundacionrescate.rescata.model.Reporte.class);
//            Bitmap map = ActivityUtils.takeScreenShot(getActivity());
//            Bitmap blurredBitmap = ActivityUtils.blur(getActivity(), map);
//            ActivityUtils.customDialogBlur(context, 0, getString(R.string.app_name), getString(R.string.question), getString(R.string.accept), true, new ActivityUtils.FinishDialog() {
//                @Override
//                public void successModalDialog(Boolean respuesta) {
//                    if(respuesta){
//                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment_content, Registro.newInstance(reporte,mascota));
////                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//                    }else{
//                        getActivity().finish();
//                    }
//                }
//            },blurredBitmap);

            AlertDialog.Builder builder;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
//            } else {
                builder = new AlertDialog.Builder(context);
//            }
            builder.setTitle("Su mascota ha sido registrada con éxito")
                    .setMessage(getString(R.string.question))
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_content, Registro.newInstance(reporte,mascota));
//                        fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            getActivity().finish();

                        }
                    })
                    .show();

        }

        @Override
        public Context getContext() {
            return context;
        }
    };



}
