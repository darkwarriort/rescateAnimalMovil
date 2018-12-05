package com.fundacionrescate.rescata.util;

import android.text.Editable;
import android.text.TextWatcher;

public abstract  class OnCustomItemSelectedListener implements TextWatcher {

    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // nothing here
    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        // nothing here
    }

    @Override
    public final void afterTextChanged(Editable editable) {
        onItemSelected(editable.toString());
    }

    protected abstract void onItemSelected(String string);
}