package com.example.uqac.unilink;


import static com.example.uqac.unilink.CustomAdapter.SORTIE;

/**
 * Created by Folgar on 03/12/2017.
 */

public class SortieStructure extends GeneralStructure{

    public SortieStructure(){}

    public SortieStructure(String date, String heure, String lieu, String description, String nombreMax, String username, String participant){
        this.id = 2;
        this.date=date;
        this.heure=heure;
        this.lieu=lieu;
        this.description=description;
        this.nombreMax=nombreMax;
        this.nombreParticipants = "1";
        this.creator=username;
        this.Participants.add(participant);
    }
}
