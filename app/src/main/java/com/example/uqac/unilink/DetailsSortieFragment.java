package com.example.uqac.unilink;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Lorane on 03/12/2017.
 */

public class DetailsSortieFragment extends GeneralFragment {

    private TextView heure;
    private TextView date;
    private TextView lieu;
    private TextView description;
    private TextView participants;
    private Button rejoindre;

    private SortieStructure sortieStructure;
    private GeneralFragment previousFragment;

    public DetailsSortieFragment(){}

    public static DetailsSortieFragment newInstance(SortieStructure sortie, GeneralFragment previousFragment){
        DetailsSortieFragment fragment = new DetailsSortieFragment();
        fragment.setDatas(sortie, previousFragment);
        return fragment;
    }

    public void setDatas(SortieStructure sortieStructure, GeneralFragment previousFragment){
        this.sortieStructure = sortieStructure;
        this.previousFragment = previousFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_details_sortie, container, false);

        heure = (TextView) view.findViewById(R.id.heure);
        date = (TextView) view.findViewById(R.id.date);
        lieu = (TextView) view.findViewById(R.id.lieu);
        description = (TextView) view.findViewById(R.id.description);
        participants = (TextView) view.findViewById(R.id.participants);
        rejoindre = (Button) view.findViewById(R.id.rejoindre);

        heure.setText(sortieStructure.heure);
        date.setText(sortieStructure.date);
        lieu.setText(sortieStructure.lieu);
        description.setText(sortieStructure.description);
        participants.setText("Insérer ici la liste des participants");

        //TODO
        // faire requête firebase pour savoir si l'utilisateur est inscrit au link et si oui cacher
        // le bouton rejoindre (rejoindre.setVisibility(View.GONE);)

        rejoindre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                // code firebase pour inscrire l'utilisateur au link
            }
        });

        return view;
    }

    /**
     * Back pressed send from activity.
     *
     * @return if event is consumed, it will return true.
     */
    @Override
    public boolean onBackPressed() {
        if(previousFragment instanceof SortiesFragment)
            ((MainActivity)getActivity()).onSortieAll();
        else
            ((MainActivity)getActivity()).onAccueil();
        return true;
    }
}
