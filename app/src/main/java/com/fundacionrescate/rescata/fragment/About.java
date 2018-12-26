package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.app.AppConfig;

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

        return v ;
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
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, new Registro());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
