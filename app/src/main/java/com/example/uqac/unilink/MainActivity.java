package com.example.uqac.unilink;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.uqac.unilink.CustomAdapter.SORTIE;
import static com.example.uqac.unilink.CustomAdapter.TABLE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private GeneralFragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header_view = navigationView.getHeaderView(0);
        TextView email = (TextView) header_view.findViewById(R.id.email);
        TextView displayName = (TextView) header_view.findViewById(R.id.displayName);

        String name = user.getDisplayName();
        for(UserInfo userInfo : user.getProviderData()){
            if(name == null && userInfo.getDisplayName() != null)
                name = userInfo.getDisplayName();
        }

        email.setText(user.getEmail());
        displayName.setText(name);

        //TODO rajouter code pour stocker les infos de l'utilisateur dans firebase

        fragment = new AccueilFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);

        else if(fragment instanceof GeneralFragment)

            if(!fragment.onBackPressed())
                super.onBackPressed();

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
                onAccueil();
                break;

            case R.id.nav_mes_links:
                onLink();
                break;

            case R.id.nav_tables:
                onTableAll();
                break;

            case R.id.nav_sorties:
                onSortieAll();
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

    public void onAccueil(){
        fragment = new AccueilFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void onLink(){
        fragment = new LinksFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void onTableAll(){

        //TODO Faire la requête firebase qui récupère les links table classés par date

        // Data temporaires pour Tables
        GeneralStructure[] mDatasetTables = {new TableStructure("10/12/17", "12:30","UQAC","test1","5"),
                new TableStructure("12/12/17", "12:30","UQAC","test2","10")};
        int[] mDatasetTypesTables = {TABLE, TABLE}; //view types

        fragment = TablesFragment.newInstance(mDatasetTables, mDatasetTypesTables);
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void onTableLaunch(GeneralStructure[] dataset, int[] detasetTypes){

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
                    onTableAll();
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
        else{
            fragment = TablesFragment.newInstance(dataset, detasetTypes);
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        }
    }

    public void onResearchTableLaunch(){
        fragment = new ResearchTablesFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void onDetailsTable(TableStructure table){
        GeneralFragment previousFragment = fragment;
        fragment = DetailsTableFragment.newInstance(table, previousFragment);
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void onSortieAll(){

        //TODO Faire la requête firebase qui récupère les links sortie classés par date


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        ref.child("sortie").addListenerForSingleValueEvent(new ValueEventListener() {
                                                               @Override
                                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                                   // Data temporaires pour Tables
                                                                   final GeneralStructure[] mDatasetSorties = new GeneralStructure[(int) Math.min(dataSnapshot.getChildrenCount(),6)];
                                                                   final int[] mDatasetTypesSorties = new int[(int) Math.min(dataSnapshot.getChildrenCount(),6)]; //view types

                                                                   int i = 0;
                                                                   for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                                                                       if (i <= 5) {

                                                                           SortieStructure sortie = eventSnapshot.getValue(SortieStructure.class);

                                                                           mDatasetSorties[i] = sortie;
                                                                           mDatasetTypesSorties[i] = SORTIE;
                                                                       i++;
                                                                       }
                                                                   }

                                                                   int taille = 0;
                                                                   for(i = 0; i < mDatasetSorties.length; i++){
                                                                       if(mDatasetSorties[i] != null)
                                                                           taille++;
                                                                   }

                                                                   GeneralStructure[] dataset = new GeneralStructure[taille];
                                                                   int[] datasetTypes = new int[taille];

                                                                   for(i=0; i<taille;i++)
                                                                       dataset[i] = mDatasetSorties[i];

                                                                   for(i=0; i<taille;i++)
                                                                       datasetTypes[i] = mDatasetTypesSorties[i];

                                                                   fragment = SortiesFragment.newInstance(mDatasetSorties, mDatasetTypesSorties);
                                                                   fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                                                               }

                                                               @Override
                                                               public void onCancelled(DatabaseError databaseError) {
                                                                   System.out.println("The read failed: " + databaseError.getCode());
                                                               }
                                                           });


    }

    public void onSortieLaunch(GeneralStructure[] dataset, int[] datasetTypes){

        if(dataset.length == 0){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Nouveau Link Sortie");
            newDialog.setMessage("Aucun Link ne correspond à vos critères de recherche. Voulez vous en créer un?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    fragment = new CreateSortiesFragment();
                    fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    onSortieAll();
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
        else{
            fragment = SortiesFragment.newInstance(dataset, datasetTypes);
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        }
    }

    public void onResearchSortieLaunch(){
        fragment = new ResearchSortiesFragment();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void onDetailsSortie(SortieStructure sortie){
        GeneralFragment previousFragment = fragment;
        fragment = DetailsSortieFragment.newInstance(sortie, previousFragment);
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

}
