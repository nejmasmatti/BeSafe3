package fr.projet.besafe.model;

import fr.projet.besafe.model.Adresse;

public class Position {
    private int idPosition;
    private float longitude;
    private float latitude;
    private Adresse adresse;

    public Position(){

    }

    public Position(int idPosition, float longitude, float latitude, Adresse adresse) {
        this.idPosition = idPosition;
        this.longitude = longitude;
        this.latitude = latitude;
        this.adresse = adresse;
    }

    public int getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }
}
