package com.example.uqac.unilink;

/**
 * Created by Folgar on 03/12/2017.
 */

public class SortieStructure extends GeneralStructure{

    public SortieStructure(String date, String heure, String lieu, String description, String nombreMax) {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        this.date=date;
        this.heure=heure;
        this.lieu=lieu;
        this.description=description;
        this.nombreMax=nombreMax;
        this.nombreParticipants = "1";
    }

    public SortieStructure() {
    }

    public SortieStructure(String date, String heure, String lieu, String description, String nombreMax, String username, String participant){
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
