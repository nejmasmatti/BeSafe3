package fr.projet.besafe.model.AlerteBeSafe;

import java.util.Date;

import fr.projet.besafe.model.Position;
import fr.projet.besafe.model.User.User;

public class AlerteVol implements IAlerte {

    private User user;
    private Position position;
    private int nivDanger;
    private int idAlerteV;

    public AlerteVol(){

    }

    public AlerteVol(User user, Position position, int nivDanger, int idAlerteV) {
        this.user = user;
        this.position = position;
        this.nivDanger = nivDanger;
        this.idAlerteV = idAlerteV;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getDanger() {
        return nivDanger;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
