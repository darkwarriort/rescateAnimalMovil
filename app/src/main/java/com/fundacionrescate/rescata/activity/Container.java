package com.fundacionrescate.rescata.activity;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.fragment.About;
import com.fundacionrescate.rescata.fragment.Credenciales;
import com.fundacionrescate.rescata.fragment.ViewMascotas;
import com.fundacionrescate.rescata.maps.Report;

public class Container extends AppCompatActivity {

    public final static int FRAGMENT_REPORTE= 1 ;
    public final static int FRAGMENT_ADOPCION= 2 ;
    public final static int FRAGMENT_LOGIN= 3 ;
    public final static int FRAGMENT_ABOUT= 4 ;

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFragmentManager = getSupportFragmentManager();

        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null){
            value = b.getInt("key");
        }
        if(value>-1){
            switch (value){
                case FRAGMENT_REPORTE:
                    setFragment(new Report());
                    setTitle(R.string.report_title_activity);

                    break;
                case FRAGMENT_ADOPCION:

                    setFragment(new ViewMascotas());
                    setTitle(R.string.view_mascota_title_activity);
                    break;
                case FRAGMENT_LOGIN:

                    setFragment(new Credenciales());
                    setTitle(R.string.login_title_activity);
                    break;
                case FRAGMENT_ABOUT:

                    setFragment(new About());
                    setTitle(R.string.about_title_activity);
                    break;
            }
        }
    }


    private void setFragment(Fragment fragment)
    {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_content, fragment);
        mFragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }else if (item.getItemId() == R.id.gotoHome) {
            finish();
            overridePendingTransition(0, 0);

            return true;
        }
        return false;
    }



    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
        {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            overridePendingTransition(0, 0);
        }

    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if ( v instanceof EditText) {
//                Rect outRect = new Rect();
//                v.getGlobalVisibleRect(outRect);
//                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
//                    v.clearFocus();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//        }
//        return super.dispatchTouchEvent( event );
//    }

}

