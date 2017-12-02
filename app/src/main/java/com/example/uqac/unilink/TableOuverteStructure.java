package com.example.uqac.unilink;

/**
 * Created by Alexis Leniau on 02/12/2017.
 */

public class TableOuverteStructure {
    public String date;
    public String heure;
    public String lieu;
    public String description;
    public String nombre;

    public TableOuverteStructure() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public TableOuverteStructure(String date,String heure,String lieu, String description,String nombre){
        this.date=date;
        this.heure=heure;
        this.lieu=lieu;
        this.description=description;
        this.nombre=nombre;
    }
}