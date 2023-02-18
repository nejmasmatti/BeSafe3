package fr.projet.besafe.modelApi;

import java.util.ArrayList;

import fr.projet.besafe.model.AlerteGouvernementale.AlerteGouvernementale;

public class AlerteGouvParDepartementGson extends ReponseApi{
    private String codeDepartement;
    private ArrayList<AlerteGouvernementale> alertes;

    public AlerteGouvParDepartementGson(boolean success, String message, String codeDepartement, ArrayList<AlerteGouvernementale> alertes) {
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

    public ArrayList<AlerteGouvernementale> getAlertes() {
        return alertes;
    }

    public void setAlertes(ArrayList<AlerteGouvernementale> alertes) {
        this.alertes = alertes;
    }
}
