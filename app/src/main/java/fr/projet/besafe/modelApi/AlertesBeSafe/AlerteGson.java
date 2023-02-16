package fr.projet.besafe.modelApi.AlertesBeSafe;

import fr.projet.besafe.model.Adresse;
import fr.projet.besafe.model.User.User;
import fr.projet.besafe.modelApi.ReponseApi;

public class AlerteGson extends ReponseApi {
    private int id;
    private User user;
    private String typeAlerte;
    private String libelle;
    private Adresse adresse;
    private int nivDanger;

    public AlerteGson(boolean success, String message, int id, User user, String typeAlerte, String libelle, Adresse adresse, int nivDanger) {
        super(success, message);
        this.id = id;
        this.user = user;
        this.typeAlerte = typeAlerte;
        this.libelle = libelle;
        this.adresse = adresse;
        this.nivDanger = nivDanger;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
