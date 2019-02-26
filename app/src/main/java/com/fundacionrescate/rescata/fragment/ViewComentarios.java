package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.adapter.Comentarios;
import com.fundacionrescate.rescata.adapter.Donaciones;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Comentario;
import com.fundacionrescate.rescata.model.Donacion;
import com.fundacionrescate.rescata.model.Reporte;
import com.fundacionrescate.rescata.model.Usuario;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fundacionrescate.rescata.app.AppConfig.URL_COMENTARIOS;
import static com.fundacionrescate.rescata.app.AppConfig.URL_DONACION;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewComentarios extends Fragment {

    Context context;
    ArrayList<Comentario> items;

    private static final String ARG_PARAM1 = "registro";

    SharedPreferences prefs;

    RecyclerView recyclerView;
    Comentarios comentariosAdapter;


    @BindView(R.id.complete_form_observacion_input)
    TextInputEditText observacion_input;
    Usuario userRegistrado= null;
    boolean isLoggeado;
    Reporte reporte ;
    public ViewComentarios() {
        // Required empty public constructor
        items = new ArrayList<>();
    }
    public static ViewComentarios newInstance(Reporte usuario) {
        ViewComentarios fragment = new ViewComentarios();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, usuario);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.reporte = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comentarios, container, false);
        ButterKnife.bind(this,v);
        getActivity().setTitle("Comentarios");

        recyclerView = (RecyclerView) v.findViewById(R.id.listComentarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        comentariosAdapter = new Comentarios(items,context);
        recyclerView.setAdapter(comentariosAdapter);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        isLoggeado = prefs.getBoolean(AppConfig.PREF_isLOGGED, false);
        String sUsuario = prefs.getString(AppConfig.PREF_USUARIO,null);
        userRegistrado = new Gson().fromJson(sUsuario, Usuario.class);
        Consulta.GETARRAY(URL_COMENTARIOS+"/"+reporte.getIdReporte(),consultaComentarios);
        return v;
    }


    Consulta.CallBackConsulta consultaComentarios = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {
                items.clear();
                items.addAll(Arrays.asList(new Gson().fromJson(response.toString(), Comentario[].class)));
                comentariosAdapter.notifyDataSetChanged();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public Context getContext() {
            return context;
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @OnClick(R.id.complete_form_guardar)
    void save(){

        if(isLoggeado){
            if(!observacion_input.getText().toString().isEmpty()){
                reporte.setObservacion(observacion_input.getText().toString());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("comentario",observacion_input.getText().toString());
                    jsonObject.put("id_animal",reporte.getIdReporte());
                    if(isLoggeado){
                        jsonObject.put("id_usuario",userRegistrado.getId_usuario());
                    }else{
                        jsonObject.put("id_usuario",reporte.getId_usuario());

                    }


                    jsonObject.put("estado","ACTIVO");


                    Consulta.POST(jsonObject, AppConfig.URL_COMENTARIOS,postUpdate);

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            else{
                final Snackbar snackbar = Snackbar.make(getView(),"No ha ingresado ninguna observacion", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }
        else{
            final Snackbar snackbar = Snackbar.make(getView(),"Debe registrarse para enviar comentarios", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }


    }

    @OnClick(R.id.complete_form_salir)
    void exit(){
        getFragmentManager().popBackStack();
    }
    Consulta.CallBackConsulta postUpdate = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {

            System.out.println(response.toString());
            Consulta.GETARRAY(URL_COMENTARIOS+"/"+reporte.getIdReporte(),consultaComentarios);

        }

        @Override
        public Context getContext() {
            return context;
        }
    };
}
