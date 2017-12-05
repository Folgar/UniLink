package com.example.uqac.unilink;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Lorane on 01/12/2017.
 */

public class ProfilFragment extends GeneralFragment {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private TextView displayName;
    private TextView email;

    public ProfilFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profil, container, false);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        email = (TextView) view.findViewById(R.id.email);
        displayName = (TextView) view.findViewById(R.id.displayName);
        displayName.setText("Nom affiché : "+user.getDisplayName());
        email.setText("Email : "+user.getEmail());


        return view;
    }




    @Override
    public boolean onBackPressed() {
        ((MainActivity)getActivity()).onAccueil();
        return true;
    }
}
