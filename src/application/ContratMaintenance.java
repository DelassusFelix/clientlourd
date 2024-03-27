package application;

import java.util.ArrayList;
import java.util.Date;

public class ContratMaintenance {
    private String numContrat;
    private Date dateSignature, dateEcheance;
    private ArrayList<Materiel> lesMaterielsAssueres;

    public String getNumContrat() {
        return numContrat;
    }

    public void setNumContrat(String numContrat) {
        this.numContrat = numContrat;
    }

    public Date getDateSignature() {
        return dateSignature;
    }

    public void setDateSignature(Date dateSignature) {
        this.dateSignature = dateSignature;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Date dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public ArrayList<Materiel> getLesMaterielsAssueres() {
        return lesMaterielsAssueres;
    }

    public void setLesMaterielsAssueres(ArrayList<Materiel> lesMaterielsAssueres) {
        this.lesMaterielsAssueres = lesMaterielsAssueres;
    }

    public ContratMaintenance(String numContrat, Date dateSignature, Date dateEcheance, ArrayList<Materiel> lesMaterielsAssueres) {
        this.numContrat = numContrat;
        this.dateSignature = dateSignature;
        this.dateEcheance = dateEcheance;
        this.lesMaterielsAssueres = lesMaterielsAssueres;
    }

}
