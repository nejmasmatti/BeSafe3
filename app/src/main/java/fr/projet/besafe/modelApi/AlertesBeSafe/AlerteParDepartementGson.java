package fr.projet.besafe.modelApi.AlertesBeSafe;

import java.util.ArrayList;

import fr.projet.besafe.model.AlerteBeSafe.Alerte;
import fr.projet.besafe.modelApi.ReponseApi;

public class AlerteParDepartementGson extends ReponseApi {
    private String codeDepartement;
    private ArrayList<Alerte> alertes;

    public AlerteParDepartementGson(boolean success, String message, String codeDepartement, ArrayList<Alerte> alertes) {
        super(success, message);
        this.codeDepartement = codeDepartement;
        this.alertes = alertes;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public ArrayList<Alerte> getAlertes() {
        return alertes;
    }

    public void setAlertes(ArrayList<Alerte> alertes) {
        this.alertes = alertes;
    }
}
