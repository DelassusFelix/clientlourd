package application;

public class GestionMateriels {

    private PersistanceSQL donnees;

    public PersistanceSQL getDonnees() {
        return donnees;
    }

    public void setDonnees(PersistanceSQL donnees) {
        this.donnees = donnees;
    }


    public GestionMateriels(PersistanceSQL donnees) {
        this.donnees = donnees;
    }


}
