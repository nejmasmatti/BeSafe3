package fr.projet.besafe.model.AlerteBeSafe;

import fr.projet.besafe.model.Adresse;

public class Alerte{

    private int id;
    private int idUser;
    private String typeAlerte;
    private String libelle;
    private Adresse adresse;
    private int nivDanger;

    public Alerte(){

    }

    public Alerte(int id, int idUser, String typeAlerte, String libelle, Adresse adresse, int nivDanger) {
        this.id = id;
        this.idUser = idUser;
        this.typeAlerte = typeAlerte;
        this.libelle = libelle;
        this.adresse = adresse;
        this.nivDanger = nivDanger;
    }

    public int getIdAlerte() {
        return id;
    }

    public void setIdAlerte(int idAlerte) {
        this.id = idAlerte;
    }

    public int getUser() {
        return idUser;
    }

    public void setUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTypeAlerte() {
        return typeAlerte;
    }

    public void setTypeAlerte(String typeAlerte) {
        this.typeAlerte = typeAlerte;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public int getNivDanger() {
        return nivDanger;
    }

    public void setNivDanger(int nivDanger) {
        this.nivDanger = nivDanger;
    }
}
