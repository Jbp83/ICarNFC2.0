package com.example.jb.icarnfc;

import java.util.Date;

/**
 * Created by Mars on 25/04/2017.
 */


    public class Voiture {


        private int id_proprietaire;
        private String nom;
        private String immatriculation;
        private String modele;
        private String marque;
        private String DateImmat;
        private String urlimage;
        private String photo;
        private int CV;
        private String id;



    public Voiture(int id_proprietaire, String nom, String immatriculation, String modele, String marque, String dateImmat, String urlimage, int cv, String id) {
        this.id_proprietaire = id_proprietaire;
        this.nom = nom;
        this.immatriculation = immatriculation;
        this.modele = modele;
        this.marque = marque;
        this.DateImmat = dateImmat;
        this.urlimage = urlimage;
        CV = cv;
        this.id = id;
        this.photo=photo;
    }


    public int getId_proprietaire() {
        return id_proprietaire;
    }

    public void setId_proprietaire(int id_proprietaire) {
        this.id_proprietaire = id_proprietaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }


    public int getCV() {
        return CV;
    }

    public void setCV(int CV) {
        this.CV = CV;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getDateImmat() {
        return DateImmat;
    }

    public void setDateImmat(String dateImmat) {
        DateImmat = dateImmat;
    }

    public String getUrlimage() {
        return urlimage;
    }

    public void setUrlimage(String urlimage) {
        this.urlimage = urlimage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

