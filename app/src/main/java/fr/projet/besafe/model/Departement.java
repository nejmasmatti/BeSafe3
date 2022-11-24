package fr.projet.besafe.model;

public class Departement {
    private int code;
    private String libelle;

    public Departement(){

    }

    public Departement(int code, String libelle){
        this.code = code;
        this.libelle = libelle;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
