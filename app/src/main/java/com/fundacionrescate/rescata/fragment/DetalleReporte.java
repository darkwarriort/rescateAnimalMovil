package com.fundacionrescate.rescata.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Especie;
import com.fundacionrescate.rescata.model.Raza;
import com.fundacionrescate.rescata.model.Reporte;
import com.fundacionrescate.rescata.model.Sexo;
import com.fundacionrescate.rescata.util.OnCustomItemSelectedListener;
import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleReporte extends Fragment {

    private static final String ARG_PARAM1 = "reporte";
    Context context;
    Reporte reporte = null ;
    private Bitmap bitmap;


    ArrayAdapter<Especie> adapterEspecie;
    ArrayAdapter<Raza> adapterRaza;

    Especie[] lstEspecie;
    Raza[] lstRaza;


    ArrayAdapter<Sexo> adapterSexo;
    Sexo[] lstSexo;

    private int PICK_IMAGE_REQUEST = 1;
    private int CAMERA_PHOTO = 2;




    @BindView(R.id.complete_form_edad_layout)
    TextInputLayout edad_layout;
    @BindView(R.id.complete_form_color_layout)
    TextInputLayout color_layout;
    @BindView(R.id.register_name_mascota_layout)
    TextInputLayout mascota_layout;



    @BindView(R.id.complete_form_descripcion_input)
    TextInputEditText descripcion_input;
    @BindView(R.id.register_name_mascota_input)
    TextInputEditText mascota_input;
    @BindView(R.id.complete_form_edad_input)
    TextInputEditText edad_input;
    @BindView(R.id.complete_form_color_input)
    TextInputEditText color_input;

    @BindView(R.id.spnSexo)
    MaterialBetterSpinner spnSexo;
    @BindView(R.id.spnSpecies)
    MaterialBetterSpinner spnEspecie;
    @BindView(R.id.spnRaza)
    MaterialBetterSpinner spnRaza;

    @BindView(R.id.imgPhoto)
    ImageView imageView;

    @BindView(R.id.abandono)
    RadioButton abandono;
    @BindView(R.id.extraviado)
    RadioButton extraviado;


    @BindView(R.id.rdgReporte)
    RadioGroup rdgReporte;

    public DetalleReporte() {
        // Required empty public constructor
        lstEspecie = new Especie[0];
        lstRaza  = new Raza[0];
        lstSexo = new Sexo[0];

    }
    public static DetalleReporte newInstance(Reporte reporte) {
        DetalleReporte fragment = new DetalleReporte();
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
        View v = inflater.inflate(R.layout.fragment_detalle_reporte, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapterEspecie= new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstEspecie);
        spnEspecie.setAdapter(adapterEspecie);

        adapterRaza = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstRaza);
        spnRaza.setAdapter(adapterRaza);

        cargaDato();

        adapterSexo= new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line, lstSexo);
        spnSexo.setAdapter(adapterSexo);


        Consulta.GETARRAY(AppConfig.URL_SEXO, consultaSexo);


        color_input.setText(reporte.getColor());
        if(reporte.getEdad()!=null) {
            edad_input.setText(String.valueOf(reporte.getEdad()));
        }
        edad_input.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        edad_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        mascota_input.setText(reporte.getNombre());

        spnEspecie.setText(reporte.getEspecie());


        Consulta.GETARRAY(AppConfig.URL_RAZA+"/"+reporte.getId_especie().longValue(),consultaRaza);
        spnEspecie.addTextChangedListener(listenerSpinnerEspecie );

        if(reporte.getFoto()!= null) {
            Glide.with(context)
                    .load(AppConfig.HOST_UPLOAD + reporte.getFoto())
                    .error(R.drawable.ic_photo_camera_black_24dp)
                    .into(imageView);
        }

        if(reporte.getDescripcion()!=null){
            descripcion_input.setText(reporte.getDescripcion());
        }

        if(reporte.getEstado_animal().equals("Abandono")){
            abandono.setChecked(true);
        }else{
            extraviado.setChecked(true);
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context= context;
    }

    private Uri filePath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data); comment this unless you want to pass your result to the activity.
        System.out.println("StartActivity");
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            System.out.println(filePath.toString());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                Consulta.POSTMultiPart(bitmap,AppConfig.URL_REPORTE_IMAGE+reporte.getIdReporte(),uploadFoto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
        if (requestCode == CAMERA_PHOTO && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            try {
                bitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(bitmap);
                Consulta.POSTMultiPart(bitmap,AppConfig.URL_REPORTE_IMAGE+reporte.getIdReporte(),uploadFoto);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }



    }





    public void cargaDato(){
        Consulta.GETARRAY(AppConfig.URL_ESPECIE, consultaEspecie);

    }

    @OnClick(R.id.complete_form_save)
    void save(){
        boolean isComplete = true;

        if(!((String) ((RadioButton)getView().findViewById(rdgReporte.getCheckedRadioButtonId())).getText()).equals("Abandono")) {
            if (mascota_input.getText().toString().isEmpty()) {
                mascota_layout.setError("Asignele un nombre a la mascota");
                isComplete =false;
            }
        }

        if(isComplete){
            reporte.setEdad(edad_input.getText().toString());

            for (Sexo r : lstSexo){
                if(r.getNombre().equals(spnSexo.getText().toString())){
                    reporte.setId_sexo(r.getId_sexo());
                    break;
                }
            }
            reporte.setDescripcion(descripcion_input.getText().toString());
            reporte.setNombre(mascota_input.getText().toString());
            reporte.setColor(color_input.getText().toString());
            reporte.setEstado_animal("INACTIVO");
            reporte.setEstado_animal((String) ((RadioButton)getView().findViewById(rdgReporte.getCheckedRadioButtonId())).getText());

            try {
                Consulta.POST(new JSONObject(new Gson().toJson(reporte)), AppConfig.URL_REPORTE_UPDATE+reporte.getId_usuario(),postUpdate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(context, "Por favor valide los datos", Toast.LENGTH_SHORT).show();
        }


    }

    @OnClick(R.id.complete_form_exit)
    void exit(){
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.imgPhoto)
    void showFileChooser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Add the buttons
        builder.setPositiveButton("Tomar foto", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                captureCameraImage();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_PHOTO);
                }
            }
        });
        builder.setNegativeButton("Elegir foto", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                selectPicture();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra("scale", true);
                intent.putExtra("outputX", 256);
                intent.putExtra("outputY", 256);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("return-data", true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        //
        positiveButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        negativeButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        neutralButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        //
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);


    }

    public class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
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


    Consulta.CallBackConsulta postUpdate = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {

            final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(context);
            dialog.setTitle(context.getString(R.string.app_name))
                    .setMessage("SE VERIFICARÁ QUE LA FOTO Y LOS DATOS SEAN CORRECTOS PARA SER PUBLICADOS. “GRACIAS”")
                    .setCancelable(false)
                    .setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            getFragmentManager().popBackStack();
                        }
                    });
            dialog.show();

        }

        @Override
        public Context getContext() {
            return context;
        }
    };


    Consulta.CallBackConsulta uploadFoto = new Consulta.CallBackConsulta() {
        @Override
        public void onError(Object response) {

        }

        @Override
        public void onSuccess(Object response) {
            try {
                System.out.println(response.toString());
                final com.fundacionrescate.rescata.model.Reporte reporteImage = new Gson().fromJson(response.toString(), com.fundacionrescate.rescata.model.Reporte.class);
                if(reporteImage.getIdReporte()!= null && reporteImage.getIdReporte().compareTo(reporte.getIdReporte())==0){

                    Glide.with(context)
                            .load(AppConfig.HOST_UPLOAD+reporteImage.getFoto())
                            .error(R.drawable.ic_photo_camera_black_24dp)
                            .into(imageView);
                    reporte.setFoto(reporteImage.getFoto());

                    System.out.println("CARGO URL DE IMAGE EN REPORTE LOCAL : " +new Gson().toJson(reporte));

                    Snackbar.make(getView(),"SE VERIFICARÁ QUE LA FOTO Y LOS DATOS SEAN CORRECTOS PARA SER PUBLICADOS. “GRACIAS”",Snackbar.LENGTH_SHORT).show();

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
//            spnRaza.setText("");
            spnRaza.setText(reporte.getRaza());


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
                if(reporte.getId_sexo()!=null){
                    for(Sexo sexo : lstSexo){
                        if(sexo.getId_sexo()== reporte.getId_sexo().longValue()){
                            spnSexo.setText(sexo.getNombre());

                            break;
                        }
                    }
                }
                //reporte.getId_sexo()

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
