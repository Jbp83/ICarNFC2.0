package com.example.jb.icarnfc.Object;

/**
 * Created by Jb on 16/05/2017.
 */

public class Utilisateur {

    private String id;
    private String nom;
    private String prenom;
    private  String status;
    private String mail;
    private  String id_etablissement;

    public Utilisateur(String id, String nom, String prenom, String status, String mail, String id_Etablissement)
    {

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.status = status;
        this.mail = mail;
        this.id_etablissement= id_Etablissement;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getId_etablissement() {
        return id_etablissement;
    }

    public void setId_etablissement(String id_etablissement) {
        this.id_etablissement = id_etablissement;
    }
}
