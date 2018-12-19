package com.fundacionrescate.rescata.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionrescate.rescata.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class About extends Fragment {


    public About() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.about_title_activity);

        return v ;
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
