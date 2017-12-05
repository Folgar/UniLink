package com.example.uqac.unilink;

/**
 * Created by Lorane on 03/12/2017.
 */

public class TableStructure extends GeneralStructure {

    public TableStructure(){}

    public TableStructure(String linkId,String date, String heure, String lieu, String description, String nombreMax, String username, String participant){
        this.id = 1;
        this.date=date;
        this.heure=heure;
        this.lieu=lieu;
        this.description=description;
        this.nombreMax=nombreMax;
        this.nombreParticipants="1";
        this.creator=username;
        this.Participants.add(participant);
        this.linkId=linkId;

    }
}
