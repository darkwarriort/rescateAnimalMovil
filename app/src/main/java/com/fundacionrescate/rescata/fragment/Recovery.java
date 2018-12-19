package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Usuario;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Recovery extends Fragment {
    Context context;

    @BindView(R.id.complete_form_name_input)
    TextInputEditText input_correo;



    public Recovery() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recovery, container, false);
        ButterKnife.bind(this,v);
        getActivity().setTitle("Recuperacion de clave");

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    Consulta.CallBackConsulta postRecuperar = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {
            System.out.println("ERROR POSTrecupera");
        }

        @Override
        public void onSuccess(Object response) {
            final Usuario usuario = new Gson().fromJson(response.toString(),Usuario.class);

            if(usuario.getId_usuario() != null && usuario.getId_usuario()>0)
            {
                Toast.makeText(context,"Revise su correo",Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }else{
                Toast.makeText(context,"Usuario no encontrado",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public Context getContext() {
            return context;
        }
    };

    @OnClick(R.id.complete_form_guardar)
    void onClickRecovery(){
        String correo = input_correo.getText().toString();

        if(!correo.isEmpty()){
            Usuario user = new Usuario();
            user.setCorreo(correo);
            try {
                Consulta.POST(new JSONObject(new Gson().toJson(user)), AppConfig.URL_USUARIO_RECOVERY,postRecuperar);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
