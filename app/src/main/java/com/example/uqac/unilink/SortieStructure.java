package com.example.uqac.unilink;

/**
 * Created by Folgar on 03/12/2017.
 */

public class SortieStructure extends GeneralStructure{

    //TODO Ajouter utilisateur créateur
    //TODO Ajouter list utilisateurs Participants
    public SortieStructure() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public SortieStructure(String date,String heure,String lieu, String description, String nombreMax){
        this.date=date;
        this.heure=heure;
        this.lieu=lieu;
        this.description=description;
        this.nombreMax=nombreMax;
        this.nombreParticipants = "1";
    }
}
