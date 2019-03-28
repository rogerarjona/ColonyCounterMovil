package com.arjona.roger.test2;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.arjona.roger.test2.Fotografias.FragmentFotografias;
import com.arjona.roger.test2.Proyectos.CrearProyectoFragment;
import com.arjona.roger.test2.Proyectos.FragmentProyectos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class menu_app extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CrearProyectoFragment.OnFragmentInteractionListener,
        FragmentProyectos.OnFragmentInteractionListener, FragmentFotografias.OnFragmentInteractionListener, FragmentDatosFotografias.OnFragmentInteractionListener {

    FloatingActionButton fab;
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contextOfApplication = getApplicationContext();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (view.getId() == R.id.fragment_proyectos_id){
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    Toast.makeText(view.getContext(), "TEST", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Creamos el cache del usuario para consultar constantemente
        saveUserData();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //Esta funcion sirve para obtener el FAB en algun otro activity y asi ocultarlo o mostrarlo
    public FloatingActionButton getFloatingActionButton() {
        return fab;
    }

    //Esta funcion es para obtener el context
    public static Context getContextOfApplication(){
        return contextOfApplication;
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
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment_layout = null;
        boolean fragment_seleccionado = false;

        if (id == R.id.nav_camera) {
            fragment_layout = new FragmentProyectos();
            fragment_seleccionado = true;
        } /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } */
        else if (id == R.id.nav_send) {
            //fragment_layout = new FragmentProyectos();
            //'fragment_seleccionado = true;
        }

        if (fragment_seleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment_layout).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void saveUserData(){
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", "roger");
        editor.putString("password", "");
        editor.commit();
    }
}
