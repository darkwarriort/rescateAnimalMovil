package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.maps.ReportsList;
import com.fundacionrescate.rescata.model.Mascota;
import com.fundacionrescate.rescata.model.Reporte;
import com.fundacionrescate.rescata.model.Sexo;
import com.fundacionrescate.rescata.model.Usuario;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteForm extends Fragment {

    private static final String ARG_PARAM1 = "reporte";
    private static final String ARG_PARAM2 = "usuario";
    private static final String ARG_PARAM3= "mascota";

    Reporte reporte ;
    Usuario usuario;


    @BindView(R.id.spnSexo)
    MaterialBetterSpinner spnSexo;
    Context context;


    @BindView(R.id.complete_form_name_input)
    TextInputEditText name_input;
    @BindView(R.id.register_name_mascota_input)
    TextInputEditText mascota_input;


    @BindView(R.id.complete_form_especie_input)
    TextInputEditText especie_input;


    @BindView(R.id.complete_form_edad_layout)
    TextInputLayout edad_layout;
    @BindView(R.id.complete_form_color_layout)
    TextInputLayout color_layout;
    @BindView(R.id.register_name_mascota_layout)
    TextInputLayout mascota_layout;



    @BindView(R.id.complete_form_raza_input)
    TextInputEditText raza_input;
    @BindView(R.id.complete_form_edad_input)
    TextInputEditText edad_input;
    @BindView(R.id.complete_form_color_input)
    TextInputEditText color_input;

//    @BindView(R.id.complete_form_sexo_inpu


    @BindView(R.id.complete_form_telefono_input)
    TextInputEditText telefono_input;

    @BindView(R.id.complete_form_direccion_input)
    TextInputEditText direccion_input;

    @BindView(R.id.complete_form_reporte_input)
    TextInputEditText reporte_input;

    Mascota mascota;

    ArrayAdapter<Sexo> adapterSexo;
    Sexo[] lstSexo;

    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    private ImageView imageView;
    //Uri to store the image uri
    private Uri filePath;

    public CompleteForm() {
        // Required empty public constructor
    }

    public static CompleteForm newInstance(Reporte reporte, Usuario usuario,Mascota mascota) {
        CompleteForm fragment = new CompleteForm();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, reporte);
        args.putParcelable(ARG_PARAM2, usuario);
        args.putParcelable(ARG_PARAM3, mascota);
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
//        void onListFragmentInteraction(Comercio item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.reporte = getArguments().getParcelable(ARG_PARAM1);
            this.usuario = getArguments().getParcelable(ARG_PARAM2);
            this.mascota = getArguments().getParcelable(ARG_PARAM3);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_complete_form, container, false);
        ButterKnife.bind(this, v);

        lstSexo = new Sexo[0];
//        lstSexo[0] = new Sexo(1,"Masculino",0,"ACTIVO");
//        lstSexo[1] = new Sexo(2,"Femenino",0,"ACTIVO");
//        lstSexo[2] = new Sexo(5,"Desconocido",0,"ACTIVO");
        getActivity().setTitle("Completar datos");

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

        name_input.setText(usuario.getNombres() + " " + usuario.getApellidos());
        name_input.setEnabled(false);
        raza_input.setText(mascota.getRaza());
        raza_input.setEnabled(false);
        especie_input.setText(mascota.getEspecie());
        especie_input.setEnabled(false);
        reporte_input.setText(mascota.getReporte());
        reporte_input.setEnabled(false);

        direccion_input.setText(mascota.getDireccion());
        direccion_input.setEnabled(false);

        telefono_input.setText(usuario.getTelefono());
        telefono_input.setEnabled(false);


        edad_input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        edad_input.setTransformationMethod(new NumericKeyBoardTransformationMethod());
//            TextViewUtils.separateGroups(edtAmount);
        edad_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        telefono_input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        telefono_input.setTransformationMethod(new NumericKeyBoardTransformationMethod());
//            TextViewUtils.separateGroups(edtAmount);
        telefono_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});



        adapterSexo= new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstSexo);
        spnSexo.setAdapter(adapterSexo);


        Consulta.GETARRAY(AppConfig.URL_SEXO, consultaSexo);



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
        boolean isComplete = true;
        if(edad_input.getText().toString().isEmpty())
        {
            isComplete =false;
            edad_layout.setErrorEnabled(true);
            edad_layout.setError("Ingrese una edad aproximada");
        }else{
            edad_layout.setErrorEnabled(false);

        }

        if(spnSexo.getText().toString().isEmpty()){
            isComplete =false;
            spnSexo.setError("Seleccione un sexo");
        }
        if(mascota_input.getText().toString().isEmpty()){
            isComplete =false;
            mascota_layout.setError("Asignele un nombre a la mascota");
        }

        if(color_input.getText().toString().isEmpty())
        {
            isComplete =false;
            color_layout.setErrorEnabled(true);
            color_layout.setError("Ingrese un color");
        }else{
            color_layout.setErrorEnabled(false);

        }
        if(isComplete){
            reporte.setEdad(edad_input.getText().toString());

            for (Sexo r : lstSexo){
                if(r.getNombre().equals(spnSexo.getText().toString())){
                    reporte.setId_sexo(r.getId_sexo());
                    break;
                }
            }
            reporte.setNombre(mascota_input.getText().toString());
            reporte.setTelefono(telefono_input.getText().toString());
            reporte.setColor(color_input.getText().toString());
            reporte.setEstado("ACTIVO");


            try {
                Consulta.POST(new JSONObject(new Gson().toJson(reporte)), AppConfig.URL_REPORTE_UPDATE+usuario.getId_usuario(),postUpdate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(context, "Por favor valide los datos", Toast.LENGTH_SHORT).show();
        }


    }


    @OnClick(R.id.imgPhoto)
     void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    Consulta.CallBackConsulta postUpdate = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            getActivity().finish();


        }

        @Override
        public Context getContext() {
            return context;
        }
    };



    Consulta.CallBackConsulta consultaSexo = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {

                lstSexo = new Gson().fromJson(response.toString(), Sexo[].class);
                adapterSexo = new ArrayAdapter<>(context,
                        android.R.layout.simple_dropdown_item_1line, lstSexo);
                spnSexo.setAdapter(adapterSexo);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public Context getContext() {
            return context;
        }
    };
    public class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data); comment this unless you want to pass your result to the activity.
        System.out.println("StartActivity");

    }
}
