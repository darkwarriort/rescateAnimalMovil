package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Mascota;
import com.fundacionrescate.rescata.model.Usuario;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.sql.SQLOutput;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Registro extends Fragment {


    Context context;

    @BindView(R.id.register_name_input)
    TextInputEditText name_input;
    @BindView(R.id.register_lastname_input)
    TextInputEditText lastname_input;

    @BindView(R.id.register_email_input)
    TextInputEditText email_input;

    @BindView(R.id.register_phone_input)
    TextInputEditText phone_input;

    @BindView(R.id.register_address_input)
    TextInputEditText address_input;

    @BindView(R.id.register_user_input)
    TextInputEditText user_input;


    @BindView(R.id.register_password_input)
    TextInputEditText password_input;



    com.fundacionrescate.rescata.model.Reporte reporte = null ;
    private static final String ARG_PARAM1 = "registro";
    private static final String ARG_PARAM2= "mascota";

    Mascota mascota = null;

    public Registro() {
        // Required empty public constructor
    }
    public static Registro newInstance(com.fundacionrescate.rescata.model.Reporte reporte,Mascota mascota) {
        Registro fragment = new Registro();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, reporte);
        args.putParcelable(ARG_PARAM2, mascota);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reporte = getArguments().getParcelable(ARG_PARAM1);
            mascota = getArguments().getParcelable(ARG_PARAM2);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registro, container, false);
        ButterKnife.bind(this, v);

        return  v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phone_input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        phone_input.setTransformationMethod(new NumericKeyBoardTransformationMethod());
//            TextViewUtils.separateGroups(edtAmount);
        phone_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});


    }

    @OnClick(R.id.register_new_button)
    void nextFormQuestion() {
        Usuario user = new Usuario();
        user.setApellidos(lastname_input.getText().toString());
        user.setNombres(name_input.getText().toString());
        user.setCorreo(email_input.getText().toString());
        user.setTelefono(phone_input.getText().toString());
        user.setDireccion(address_input.getText().toString());
        user.setUsuario(user_input.getText().toString());
        user.setContrasena(password_input.getText().toString());
        user.setEstado("ACTIVO");

        try {
            Consulta.POST(new JSONObject(new Gson().toJson(user)), AppConfig.URL_USUARIO,postAgregar);
        }catch (Exception e){
            e.printStackTrace();
        }



    }



    Consulta.CallBackConsulta postAgregar = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            /*
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, new CompleteForm());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
            */

            System.out.println(response);
            if(reporte!=null && mascota !=null){
                final Usuario usuario = new Gson().fromJson(response.toString(),Usuario.class);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_content, CompleteForm.newInstance(reporte,usuario,mascota));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }else{
                getActivity().finish();
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
}
