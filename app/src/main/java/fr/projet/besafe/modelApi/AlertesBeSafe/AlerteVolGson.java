package fr.projet.besafe.modelApi.AlertesBeSafe;

import fr.projet.besafe.modelApi.AReponseApi;

public class AlerteVolGson extends AReponseApi {
    private int RefUser;
    private int RefPosition;
    private int nivDanger;
    private int idAlerteV;

    public AlerteVolGson(boolean success, String message, int refUser, int refPosition, int nivDanger, int idAlerteV) {
        super(success, message);
        RefUser = refUser;
        RefPosition = refPosition;
        this.nivDanger = nivDanger;
        this.idAlerteV = idAlerteV;
    }

    public void toAlertVol(){

    }

    public int getRefUser() {
        return RefUser;
    }

    public void setRefUser(int refUser) {
        RefUser = refUser;
    }

    public int getRefPosition() {
        return RefPosition;
    }

    public void setRefPosition(int refPosition) {
        RefPosition = refPosition;
    }

    public int getNivDanger() {
        return nivDanger;
    }

    public void setNivDanger(int nivDanger) {
        this.nivDanger = nivDanger;
    }

    public int getIdAlerteV() {
        return idAlerteV;
    }

    public void setIdAlerteV(int idAlerteV) {
        this.idAlerteV = idAlerteV;
    }
}
