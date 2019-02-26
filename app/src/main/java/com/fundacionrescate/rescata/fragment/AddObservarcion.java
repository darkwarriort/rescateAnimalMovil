package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Reporte;
import com.fundacionrescate.rescata.model.Sexo;
import com.fundacionrescate.rescata.model.Usuario;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddObservarcion extends Fragment {

    private static final String ARG_PARAM1 = "reporte";
    Context context;
    Reporte reporte = null ;

    SharedPreferences prefs;

    Usuario userRegistrado= null;
    boolean isLoggeado;

    @BindView(R.id.register_name_mascota_input)
    TextInputEditText mascota_input;
    @BindView(R.id.complete_form_especie_input)
    TextInputEditText especie_input;
    @BindView(R.id.complete_form_raza_input)
    TextInputEditText raza_input;
    @BindView(R.id.complete_form_edad_input)
    TextInputEditText edad_input;
    @BindView(R.id.complete_form_color_input)
    TextInputEditText color_input;
    @BindView(R.id.complete_form_reporte_input)
    TextInputEditText reporte_input;
    @BindView(R.id.complete_form_sexo_input)
    TextInputEditText sexo_input;
    @BindView(R.id.complete_form_descripcion_input)
    TextInputEditText descripcion_input;
    @BindView(R.id.complete_form_observacion_input)
    TextInputEditText observacion_input;
    @BindView(R.id.imgPhoto)
    ImageView imageView;

    @BindView(R.id.layoutLogeado)
    LinearLayout layoutLogeado;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.complete_form_save)
    Button complete_form_save;

    public AddObservarcion() {
        // Required empty public constructor
    }
    public static AddObservarcion newInstance(Reporte reporte) {
        AddObservarcion fragment = new AddObservarcion();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, reporte);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(getArguments().getParcelable(ARG_PARAM1)!=null){
                reporte = getArguments().getParcelable(ARG_PARAM1);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_observarcion, container, false);
        ButterKnife.bind(this,v);

        getActivity().setTitle("Detalle Mascota");

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        isLoggeado = prefs.getBoolean(AppConfig.PREF_isLOGGED, false);
        String sUsuario = prefs.getString(AppConfig.PREF_USUARIO,null);
        userRegistrado = new Gson().fromJson(sUsuario, Usuario.class);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        isLoggeado = prefs.getBoolean(AppConfig.PREF_isLOGGED, false);

        if(isLoggeado){
            layoutLogeado.setVisibility(View.VISIBLE);
            complete_form_save.setVisibility(View.VISIBLE);
        }else{
            layoutLogeado.setVisibility(View.GONE);
            complete_form_save.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        raza_input.setText(reporte.getRaza());
        raza_input.setEnabled(false);

        especie_input.setText(reporte.getEspecie());
        especie_input.setEnabled(false);

        reporte_input.setText(reporte.getEstado_animal());
        reporte_input.setEnabled(false);

        if(reporte.getEdad()!=null) {
            edad_input.setText(reporte.getEdad());
            edad_input.setEnabled(false);
        }else{
            edad_input.setEnabled(false);
        }
        if(reporte.getDescripcion()!=null){
            descripcion_input.setText(reporte.getDescripcion());
            descripcion_input.setEnabled(false);
        }else{
            descripcion_input.setEnabled(false);
        }

        if(reporte.getColor()!=null){
            color_input.setText(reporte.getDescripcion());
            color_input.setEnabled(false);
        }else{
            color_input.setEnabled(false);
        }

        if(reporte.getNombre()!=null){
            mascota_input.setText(reporte.getNombre());
            mascota_input.setEnabled(false);
        }else{
            mascota_input.setEnabled(false);
        }
        if(reporte.getDescripcion()!=null){
            descripcion_input.setText(reporte.getDescripcion());
            descripcion_input.setEnabled(false);
        }else{
            descripcion_input.setEnabled(false);
        }

        if(reporte.getFoto()!=null){
            Glide.with(context)
                    .load(AppConfig.HOST_UPLOAD+reporte.getFoto())
                    .error(R.drawable.ic_photo_camera_black_24dp)
                    .into(imageView);

        }

        if(reporte.getId_sexo()!= null){
            Consulta.GETARRAY(AppConfig.URL_SEXO, consultaSexo);
        }else{
            sexo_input.setEnabled(false);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }




    @OnClick(R.id.complete_form_exit)
    void exit(){
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.complete_form_view)
    void gotoComentarios(){
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, ViewComentarios.newInstance(reporte));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @OnClick(R.id.complete_form_save)
    void save(){
        try {
            if(checkbox.isChecked()){
                reporte.setObservacion(observacion_input.getText().toString());
                reporte.setEstado_animal("Encontrado");
                Consulta.POST(new JSONObject(new Gson().toJson(reporte)), AppConfig.URL_REPORTE_UPDATE+reporte.getId_usuario(),postUpdate);
            }else{
                if(!observacion_input.getText().toString().isEmpty()){
                    reporte.setObservacion(observacion_input.getText().toString());
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("comentario",observacion_input.getText().toString());
                        jsonObject.put("id_animal",reporte.getIdReporte());
                        if(isLoggeado){
                            jsonObject.put("id_usuario",userRegistrado.getId_usuario());
                        }else{
                            jsonObject.put("id_usuario",Long.parseLong("0"));

                        }


                        jsonObject.put("estado","ACTIVO");


                        Consulta.POST(new JSONObject(new Gson().toJson(reporte)), AppConfig.URL_COMENTARIOS,postUpdate);

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
                else{
                    final Snackbar snackbar = Snackbar.make(getView(),"No ha ingresado ninguna observacion", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    Consulta.CallBackConsulta postUpdate = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            getFragmentManager().popBackStack();
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
                Sexo[] lstSexo;

                lstSexo = new Gson().fromJson(response.toString(), Sexo[].class);

                if(reporte.getId_sexo()!=null){
                    for(Sexo sexo : lstSexo){
                        if(sexo.getId_sexo()== reporte.getId_sexo().longValue()){
                            sexo_input.setText(sexo.getNombre());
                            sexo_input.setEnabled(false);
                            break;
                        }
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public Context getContext() {
            return context;
        }
    };
}
