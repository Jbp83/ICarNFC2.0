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
        private Date DateImmat;
        private int CV;


        public Voiture(int id_proprietaire,String nom, String immatriculation, String modele, Date dateImmat, int cv) {
            this.id_proprietaire = id_proprietaire;
            this.nom=nom;
            this.immatriculation = immatriculation;
            this.modele = modele;
            DateImmat = dateImmat;
            CV = cv;
        }

        public int getId_proprietaire() {
            return id_proprietaire;
        }

        public void setId_proprietaire(int id_proprietaire) {
            this.id_proprietaire = id_proprietaire;
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

        public Date getDateImmat() {
            return DateImmat;
        }

        public void setDateImmat(Date dateImmat) {
            DateImmat = dateImmat;
        }

        public int getCV() {
            return CV;
        }

        public void setCV(int CV) {
            this.CV = CV;
        }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}

