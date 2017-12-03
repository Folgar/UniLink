package com.example.uqac.unilink;

/**
 * Created by Folgar on 03/12/2017.
 */

public class SortieStructure {
    public String date;
    public String heure;
    public String lieu;
    public String description;
    public String nombre;

    public SortieStructure() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public SortieStructure(String date,String heure,String lieu, String description,String nombre){
        this.date=date;
        this.heure=heure;
        this.lieu=lieu;
        this.description=description;
        this.nombre=nombre;
    }
}
