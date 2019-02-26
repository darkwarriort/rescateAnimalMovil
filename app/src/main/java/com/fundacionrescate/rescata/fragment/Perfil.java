package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Usuario;
import com.fundacionrescate.rescata.util.Security;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Perfil extends Fragment {
    @BindView(R.id.register_password_input)
    TextInputEditText password_input;
    @BindView(R.id.register_password_layout)
    TextInputLayout password_layout;
    @BindView(R.id.register_new_password_input)
    TextInputEditText new_password_input;
    @BindView(R.id.register_new_password_layout)
    TextInputLayout new_password_layout;

    Usuario userRegistrado= null;

    Context context;
    SharedPreferences prefs;
    public Perfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        ButterKnife.bind(this,v);

        getActivity().setTitle("Cambio de clave");
        return v ;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sUsuario = prefs.getString(AppConfig.PREF_USUARIO,null);
        userRegistrado = new Gson().fromJson(sUsuario, Usuario.class);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @OnClick(R.id.register_new_button)
    void cambiarClave(){

        boolean berror = false;
        if(Security.MD5(password_input.getText().toString()).equals(userRegistrado.getContrasena())){
            if(new_password_input.getText().toString().isEmpty()){
                new_password_layout.setErrorEnabled(true);
                new_password_layout.setError("Por favor ingrese la clave");
                berror = true;
            }else{
                if(!isPasswordValid(new_password_input.getText().toString())){
                    new_password_layout.setErrorEnabled(true);
                    new_password_layout.setError("La clave debe tener entre 6 u 8 caracteres");
                    berror = true;
                }else{
                    new_password_layout.setErrorEnabled(false);
                }

            }

            if(!berror){


                userRegistrado.setContrasena(Security.MD5(new_password_input.getText().toString()));

                try {
                    Consulta.POST(new JSONObject(new Gson().toJson(userRegistrado)), AppConfig.URL_USUARIO,postAgregar);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            Snackbar.make(getView(),"Claves no coinciden", Snackbar.LENGTH_SHORT).show();
        }
    }
    public boolean isPasswordValid(String clave){
        if(clave.length()>=8){
            Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
            Matcher m = p.matcher(clave);
//            return clave.matches("/^[0-9a-zA-Z]+$/");
            return m.find();
        }
        return false;

    }

    Consulta.CallBackConsulta postAgregar = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {


            System.out.println(response);
              final Usuario usuario = new Gson().fromJson(response.toString(),Usuario.class);

                if(usuario.getId_usuario() != null && usuario.getId_usuario()>0)
                {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(AppConfig.PREF_USUARIO,response.toString());
                    editor.putBoolean(AppConfig.PREF_isLOGGED,true);
                    editor.commit();

                    getFragmentManager().popBackStack();

                }else{
                    Snackbar.make(getView(), "No se pudo realizar el cambio de clave, intente nuevamente", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }




        }

        @Override
        public Context getContext() {
            return context;
        }
    };

}
