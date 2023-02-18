package fr.projet.besafe.model.AlerteGouvernementale;

public class AlerteGouvernementale {
    private int id;
    private String libelle;
    private String typeAlerte;
    private int idVille;
    private String code;


    public AlerteGouvernementale(){

    }

    public AlerteGouvernementale(int id, String libelle, String typeAlerte, int idVille, String code) {
        this.id = id;
        this.libelle = libelle;
        this.typeAlerte = typeAlerte;
        this.idVille = idVille;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getTypeAlerte() {
        return typeAlerte;
    }

    public void setTypeAlerte(String typeAlerte) {
        this.typeAlerte = typeAlerte;
    }

    public int getIdVille() {
        return idVille;
    }

    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
