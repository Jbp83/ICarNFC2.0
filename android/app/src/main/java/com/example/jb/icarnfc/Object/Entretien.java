package com.example.jb.icarnfc.Object;

/**
 * Created by mars on 15/05/2017.
 */

public class Entretien {


    private int id ;
    private String datecreation;
    private String idvoiture;
    private String idutilisateur;
    private String idmecanicien;
    private String description;

    public Entretien(int id, String datecreation, String idvoiture, String idutilisateur, String idmecanicien, String description) {
        this.id = id;
        this.datecreation = datecreation;
        this.idvoiture = idvoiture;
        this.idutilisateur = idutilisateur;
        this.idmecanicien = idmecanicien;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public String getIdvoiture() {
        return idvoiture;
    }

    public void setIdvoiture(String idvoiture) {
        this.idvoiture = idvoiture;
    }

    public String getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(String idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public String getIdmecanicien() {
        return idmecanicien;
    }

    public void setIdmecanicien(String idmecanicien) {
        this.idmecanicien = idmecanicien;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
