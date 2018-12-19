package com.fundacionrescate.rescata.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.KeyboardView;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Usuario;
import com.fundacionrescate.rescata.util.Security;
import com.google.gson.Gson;
import com.rw.keyboardlistener.KeyboardUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class Credenciales extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Context context;
    SharedPreferences prefs;

    @BindView(R.id.login_user_input)
    TextInputEditText user_input;
    @BindView(R.id.login_password_input)
    TextInputEditText password_input;

    @BindView(R.id.login_user_layout)
    TextInputLayout user_layout;
    @BindView(R.id.login_password_layout)
    TextInputLayout password_layout;

    @BindView(R.id.txtRecovery)
    TextView txtRecovery;

    @BindView(R.id.imgLogo)
    ImageView imgLogo;


    public Credenciales() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_credenciales, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle("Inicio de sesion");
        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        KeyboardUtils.addKeyboardToggleListener((Activity) context, new KeyboardUtils.SoftKeyboardToggleListener()
        {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible)
            {
                if(isVisible){
                    imgLogo.setVisibility(View.GONE);
                }else{
                    imgLogo.setVisibility(View.VISIBLE);
                }
            }
        });
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


    @OnClick(R.id.txtRecovery)
    void recoveryPassword(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, new Recovery());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.login_registrar_button)
    void gotoRegistro() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, new Registro());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.login_ingresar_button)
    void validaForm() {
        boolean berror = false;
        if(user_input.getText().toString().isEmpty()){
            user_layout.setErrorEnabled(true);
            user_layout.setError("Por favor ingrese el usuario");

            berror = true;
        }else {
            user_layout.setErrorEnabled(false);

        }
        if(password_input.getText().toString().isEmpty()){
            password_layout.setErrorEnabled(true);
            password_layout.setError("Por favor ingrese la contraseña");
            berror = true;
        }else{
            password_layout.setErrorEnabled(false);

        }

        if(!berror){
            Usuario user = new Usuario();

            user.setUsuario(user_input.getText().toString());
            user.setContrasena(Security.MD5(password_input.getText().toString()));
            try {
                Consulta.POST(new JSONObject(new Gson().toJson(user)), AppConfig.URL_USUARIO_VALIDA,postValidar);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    Consulta.CallBackConsulta postValidar= new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {
            System.out.println("ERROR CREDENCIALES");
            Toast.makeText(context,"Usuario o contraseña incorrecta",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onSuccess(Object response) {
//            final Usuario usuario = new Gson().fromJson(response.toString(),Usuario.class);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString(AppConfig.PREF_USUARIO,response.toString());
            editor.putBoolean(AppConfig.PREF_isLOGGED,true);
            editor.commit();

            getActivity().finish();

        }

        @Override
        public Context getContext() {
            return context;
        }
    };

}
