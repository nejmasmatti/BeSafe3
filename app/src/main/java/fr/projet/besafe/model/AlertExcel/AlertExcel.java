package fr.projet.besafe.model.AlertExcel;

import java.util.ArrayList;
import java.util.Map;

public class AlertExcel {
    private int idAlert;
    private String libelleAlerte;
    private int numDepartement;
    private int nbCrime;
    private int mois;
    private int annee;


    public AlertExcel(){

    }

    public AlertExcel(String libelleAlerte, int numDepartement, int nbCrime, int mois, int annee){
        this.libelleAlerte = libelleAlerte;
        this.numDepartement = numDepartement;
        this.nbCrime = nbCrime;
        this.annee = annee;
        this.mois = mois;
    }


    public int getIdAlert() {
        return idAlert;
    }

    public void setIdAlert(int idAlerte) {
        this.idAlert = idAlerte;
    }

    public int getNumDepartement() {
        return numDepartement;
    }

    public void setNumDepartement(int numDepartement) {
        this.numDepartement = numDepartement;
    }

    public int getNbCrime() {
        return nbCrime;
    }

    public void setNbCrime(int nbCrime) {
        this.nbCrime = nbCrime;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getLibelleAlerte() {
        return libelleAlerte;
    }

    public void setLibelleAlerte(String libelleAlerte) {
        this.libelleAlerte = libelleAlerte;
    }

    public void selectAlertExel(){

    }





}
