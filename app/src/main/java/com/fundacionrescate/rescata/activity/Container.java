package com.fundacionrescate.rescata.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fundacionrescate.rescata.R;
import com.fundacionrescate.rescata.fragment.About;
import com.fundacionrescate.rescata.fragment.Credenciales;
import com.fundacionrescate.rescata.fragment.Servicios;
import com.fundacionrescate.rescata.fragment.ViewMascotas;
import com.fundacionrescate.rescata.fragment.ViewProductos;
import com.fundacionrescate.rescata.maps.Report;

public class Container extends AppCompatActivity {

    public final static int FRAGMENT_REPORTE= 1 ;
    public final static int FRAGMENT_ADOPCION= 2 ;
    public final static int FRAGMENT_LOGIN= 3 ;
    public final static int FRAGMENT_ABOUT= 4 ;
    public final static int FRAGMENT_SERVICE= 5 ;
    public final static int FRAGMENT_PRODUCT= 6 ;

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Image request code


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
                case FRAGMENT_SERVICE:

                    setFragment(new Servicios());
                    setTitle(R.string.service_title_activity);
                    break;
                case FRAGMENT_PRODUCT:

                    setFragment(new ViewProductos());
                    setTitle(R.string.productos_title_activity);
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
        View v = getCurrentFocus();
        if ( v instanceof EditText) {
            Rect outRect = new Rect();
            v.getGlobalVisibleRect(outRect);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        }
        if(getSupportFragmentManager().getBackStackEntryCount() > 0)
        {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            overridePendingTransition(0, 0);
        }

    }

//        @Override
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

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }



}

