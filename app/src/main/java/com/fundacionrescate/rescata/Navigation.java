package com.fundacionrescate.rescata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.fundacionrescate.rescata.activity.Container;
import com.fundacionrescate.rescata.app.AppConfig;
import com.fundacionrescate.rescata.fragment.About;
import com.fundacionrescate.rescata.fragment.CompleteForm;
import com.fundacionrescate.rescata.fragment.Credenciales;
import com.fundacionrescate.rescata.fragment.ViewProductos;
import com.fundacionrescate.rescata.maps.Report;
import com.fundacionrescate.rescata.maps.ReportsList;
import com.fundacionrescate.rescata.model.Usuario;
import com.google.gson.Gson;

import org.w3c.dom.Text;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    NavigationView navigationView = null;
    SharedPreferences prefs;
    boolean isLoggeado = false;
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFragment( new ReportsList());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        textView = headerview.findViewById(R.id.txtUserName);
        navigationView.setNavigationItemSelectedListener(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Menu menu = navigationView.getMenu();
        reloadMenu();
    }
    void reloadMenu(){
        Menu menu = navigationView.getMenu();
        isLoggeado = prefs.getBoolean(AppConfig.PREF_isLOGGED, false);

        if(isLoggeado){
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_login).setVisible(false);
            String sUsuario = prefs.getString(AppConfig.PREF_USUARIO,null);
            Usuario userRegistrado = new Gson().fromJson(sUsuario, Usuario.class);
            textView.setText(userRegistrado.getUsuario());
        }else{
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_login).setVisible(true);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }
    private void setFragment(Fragment fragment){

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content,fragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_report) {
            // Handle the camera action
            setFragment( new ReportsList());
        } else if (id == R.id.nav_service) {
            Intent modulCNB = new Intent(Navigation.this, Container.class);
            Bundle b = new Bundle();
            b.putInt("key", Container.FRAGMENT_SERVICE); //Your id
            modulCNB.putExtras(b); //Put your id to your next Intent
            startActivity(modulCNB);
            overridePendingTransition(0, 0);
        }else if (id == R.id.nav_adoption) {
            Intent modulCNB = new Intent(Navigation.this, Container.class);
            Bundle b = new Bundle();
            b.putInt("key", Container.FRAGMENT_ADOPCION); //Your id
            modulCNB.putExtras(b); //Put your id to your next Intent
            startActivity(modulCNB);
            overridePendingTransition(0, 0);
        } else if (id == R.id.nav_product) {
//            setFragment(new ViewProductos());

            Intent modulCNB = new Intent(Navigation.this, Container.class);
            Bundle b = new Bundle();
            b.putInt("key", Container.FRAGMENT_PRODUCT); //Your id
            modulCNB.putExtras(b); //Put your id to your next Intent
            startActivity(modulCNB);
            overridePendingTransition(0, 0);
        } else if (id == R.id.nav_about) {
//            setFragment(new About());
            Intent modulCNB = new Intent(Navigation.this, Container.class);
            Bundle b = new Bundle();
            b.putInt("key", Container.FRAGMENT_ABOUT); //Your id
            modulCNB.putExtras(b); //Put your id to your next Intent
            startActivity(modulCNB);
            overridePendingTransition(0, 0);
        } else if (id == R.id.nav_login) {
//            setFragment(new Credenciales());
            Intent modulCNB = new Intent(Navigation.this, Container.class);
            Bundle b = new Bundle();
            b.putInt("key", Container.FRAGMENT_LOGIN); //Your id
            modulCNB.putExtras(b); //Put your id to your next Intent
            startActivity(modulCNB);
            overridePendingTransition(0, 0);
        }  else if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString(AppConfig.PREF_USUARIO,null);
            editor.putBoolean(AppConfig.PREF_isLOGGED,false);
            editor.commit();
            setFragment( new ReportsList());

            reloadMenu();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}
