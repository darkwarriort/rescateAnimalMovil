package com.fundacionrescate.rescata.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.maps.ReportsList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Question extends Fragment {


    public Question() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this, v);

        return v;
    }


    @OnClick(R.id.question_register)
    void nextFormReport() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, new Registro());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @OnClick(R.id.question_end)
    void exitForm() {
        getActivity().finish();
    }

}
