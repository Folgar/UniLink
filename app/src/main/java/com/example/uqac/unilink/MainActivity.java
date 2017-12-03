package com.example.uqac.unilink;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.uqac.unilink.CustomAdapter.SORTIE;
import static com.example.uqac.unilink.CustomAdapter.TABLE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private Bundle bundle;

    // Data temporaires pour Tables
    private String[] mDatasetTables = {"Table1", "Table2", "Table3", "Table4", "Table5", "Table6"};
    private int mDatasetTypesTables[] = {TABLE, TABLE, TABLE, TABLE, TABLE, TABLE}; //view types

    // Data temporaires pour Tables
    private String[] mDatasetSorties = {"Sortie1", "Sortie2", "Sortie3", "Sortie4", "Sortie5", "Sortie6"};
    private int mDatasetTypesSorties[] = {SORTIE, SORTIE, SORTIE, SORTIE, SORTIE, SORTIE}; //view types

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragment = new AccueilFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_deconnection) {
            onLogout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        switch(item.getItemId()){
            case R.id.nav_accueil:
                fragment = new AccueilFragment();
                break;

            case R.id.nav_mes_links:
                fragment = new LinksFragment();
                break;

            case R.id.nav_tables:
                onTableLaunch(mDatasetTables,mDatasetTypesTables);
                break;

            case R.id.nav_sorties:
                onSortieLaunch(mDatasetSorties,mDatasetTypesSorties);
                break;

            case R.id.nav_trajets:
                fragment = new TrajetsFragment();
                break;

            case R.id.nav_covoiturage:
                fragment = new CovoiturageFragment();
                break;

            case R.id.nav_calendrier:
                fragment = new CalendrierFragment();
                break;

            case R.id.nav_profil:
                fragment = new ProfilFragment();
                break;

            case R.id.nav_deconnexion:
                onLogout();
                break;

            default:
                break;
        }

        if(fragment != null){
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onLogout(){
        //logging out the user
        firebaseAuth.signOut();
        LoginManager.getInstance().logOut();
        //closing activity
        finish();
        //starting login activity
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onTableLaunch(String[] dataset, int[] detasetTypes){

        if(dataset.length == 0){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Nouveau Link Table");
            newDialog.setMessage("Aucun Link ne correspond à vos critères de recherche. Voulez vous en créer un?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    fragment = new CreateTablesFragment();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    fragment = new TablesFragment();
                    bundle = new Bundle();
                    bundle.putStringArray("dataset", mDatasetTables);
                    bundle.putIntArray("datasetTypes", mDatasetTypesTables);
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
        else{
            fragment = new TablesFragment();
            bundle = new Bundle();
            bundle.putStringArray("dataset", dataset);
            bundle.putIntArray("datasetTypes", detasetTypes);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        }


    }

    public void onResearchTableLaunch(){
        fragment = new ResearchTablesFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void onSortieLaunch(String[] dataset, int[] detasetTypes){
        fragment = new SortiesFragment();
        bundle = new Bundle();
        bundle.putStringArray("dataset", dataset);
        bundle.putIntArray("datasetTypes", detasetTypes);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void onResearchSortieLaunch(){
        fragment = new ResearchSortiesFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

}
