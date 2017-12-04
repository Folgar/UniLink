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

public class ProfilFragment extends GeneralFragment implements View.OnClickListener{

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonDatabase;

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

        textViewUserEmail = (TextView) view.findViewById(R.id.textViewUserEmail);
        buttonDatabase = (Button) view.findViewById(R.id.buttonDatabase);

        //displaying logged in user name
        textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonDatabase.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == buttonDatabase){
            onDatabase();
        }
    }

    public void onDatabase(){
        textViewUserEmail.setText("Data !");
    }

    @Override
    public boolean onBackPressed() {
        ((MainActivity)getActivity()).onAccueil();
        return true;
    }
}
