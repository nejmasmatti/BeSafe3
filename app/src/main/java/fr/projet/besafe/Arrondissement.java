package fr.projet.besafe;

public class Arrondissement {

    private final String name = "PARIS";
    private int arrondissement, degreIncident;

    public Arrondissement(){
    }

    public String getName() {
        return name;
    }

    public int getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(int arrondissement) {
        this.arrondissement = arrondissement;
    }

    public int getDegreIncident(){
        return degreIncident;
    }

    public void setDegreIncident(int degreIncident){
        this.degreIncident = degreIncident;
    }



}
