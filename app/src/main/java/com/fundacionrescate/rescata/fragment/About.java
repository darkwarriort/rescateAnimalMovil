package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.cnx.Consulta;
import com.fundacionrescate.rescata.model.Participante;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class About extends Fragment {

    Context context ;
    boolean isLoggeado;
    SharedPreferences prefs;


    private WebView browser;


    @BindView(R.id.register_new_button)
    Button register_new_button;

    public About() {
        // Required empty public constructor
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
        View v= inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.about_title_activity);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        browser = (WebView) v.findViewById(R.id.webViewAbout);
        browser.addJavascriptInterface(this, "Android");
        // esto hace que la apertura de nuevas paginas sea en el mismo webView, y no en el AndroidWebBrowser
        browser.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        return v ;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        open("about.html");

    }
    @JavascriptInterface
    public void toast (String option){
        Toast.makeText(getContext(), option, Toast.LENGTH_LONG).show();
    }
    public void open(final  String tmp) {
        final String url = "file:///android_asset/"+tmp;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                browser.getSettings().setLoadsImagesAutomatically(true);
                browser.getSettings().setJavaScriptEnabled(true);
                browser.getSettings().setDomStorageEnabled(true);
                browser.loadUrl(url);

            }
        });
        //    System.out.println(url);
        getActivity().supportInvalidateOptionsMenu();

    }


    @Override
    public void onResume() {
        super.onResume();
        isLoggeado = prefs.getBoolean(AppConfig.PREF_isLOGGED, false);
        if(isLoggeado){
            register_new_button.setVisibility(View.GONE);
        }else{
            register_new_button.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.register_new_button)
    void Registrar(){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(context.getString(R.string.app_name))
                .setMessage("Desea formar parte de la app de cola?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_content, new Registro());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();

    }


}
