package fr.projet.besafe.model.AlerteBeSafe;

import java.util.Date;

import fr.projet.besafe.model.User.User;

public class AlerteVol implements IAlerte {

    private int nivDanger;
    private Date dateCreationAlerte;
    private User userAlerte;

    public AlerteVol(){

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getDanger() {
        return nivDanger;
    }

    public int getNivDanger() {
        return nivDanger;
    }

    public void setNivDanger(int nivDanger) {
        this.nivDanger = nivDanger;
    }

    public Date getDateCreationAlerte() {
        return dateCreationAlerte;
    }

    public void setDateCreationAlerte(Date dateCreationAlerte) {
        this.dateCreationAlerte = dateCreationAlerte;
    }

    public User getUserAlerte() {
        return userAlerte;
    }

    public void setUserAlerte(User userAlerte) {
        this.userAlerte = userAlerte;
    }
}
