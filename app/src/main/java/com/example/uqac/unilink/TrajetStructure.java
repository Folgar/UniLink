package com.example.uqac.unilink;

/**
 * Created by Lorane on 03/12/2017.
 */

public class TrajetStructure extends GeneralStructure {

    public TrajetStructure(String date,String heure,String lieu, String description,String nombreMax){
        this.date=date;
        this.heure=heure;
        this.lieu=lieu;
        this.description=description;
        this.nombreMax=nombreMax;
        this.nombreParticipants="1";
    }
}
