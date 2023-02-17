package fr.projet.besafe.modelApi.AlertesBeSafe;

import java.util.ArrayList;
import java.util.List;

import fr.projet.besafe.model.Adresse;
import fr.projet.besafe.model.AlerteBeSafe.Alerte;
import fr.projet.besafe.model.User.User;
import fr.projet.besafe.modelApi.ReponseApi;

public class AlerteParUserGson extends ReponseApi {
    private ArrayList<Alerte> alertes;

    public AlerteParUserGson(boolean success, String message, ArrayList<Alerte> alertes) {
        super(success, message);
        this.alertes = alertes;
    }

    public ArrayList<Alerte> getAlertes() {
        return alertes;
    }

    public void setAlertes(ArrayList<Alerte> alertes) {
        this.alertes = alertes;
    }
}
