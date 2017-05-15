package com.example.jb.icarnfc.Object;

/**
 * Created by Jb on 15/05/2017.
 */

public class Entreprise {

    private String id;
    private String nom;
    private  String siren;
    private  String adresse;
    private String telephone;


    public Entreprise(String id, String Nom,String Siren,String Adresse,String Telephone)
    {

        this.id = id;
        this.nom = Nom;
        this.siren = Siren;
        this.adresse = Adresse;
        this.telephone = Telephone;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
