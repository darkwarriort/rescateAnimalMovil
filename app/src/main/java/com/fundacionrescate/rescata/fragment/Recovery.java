package com.fundacionrescate.rescata.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundacionrescate.rescata.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Recovery extends Fragment {
    Context context;

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


}
