package com.fundacionrescate.rescata.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionrescate.rescata.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Donaciones extends Fragment {


    public Donaciones() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_donaciones, container, false);
        getActivity().setTitle("Donaciones");

        ButterKnife.bind(this, v);
        return v;
    }

}
