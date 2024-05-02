package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class ContratMaintenance {
    private int numContrat;
    private Date dateSignature, dateEcheance;
    private ArrayList<Materiel> lesMaterielsAssures;

    public int getNumContrat() {
        return numContrat;
    }

    public void setNumContrat(int numContrat) {
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

    public ArrayList<Materiel> getLesMaterielsAssures() {
        return lesMaterielsAssures;
    }

    public void setLesMaterielsAssures(ArrayList<Materiel> lesMaterielsAssures) {
        this.lesMaterielsAssures = lesMaterielsAssures;
    }

    public ContratMaintenance(int numContrat, Date dateSignature, Date dateEcheance, ArrayList<Materiel> lesMaterielsAssures) {
        this.numContrat = numContrat;
        this.dateSignature = dateSignature;
        this.dateEcheance = dateEcheance;
        this.lesMaterielsAssures = lesMaterielsAssures;
    }



    public boolean estValide() {
        Date today = new Date(); // Date actuelle
        return today.after(dateSignature) && today.before(dateEcheance);
    }


    public int getJoursRestants() {
        long diffInMillis = dateEcheance.getTime() - new Date().getTime();
        long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);
        return (int) diffInDays;
    }


    public void ajouteMateriel(Materiel unMateriel) {
        // Comparer la date de signature avec la date d'installation du matériel
        if (dateSignature.before(unMateriel.getDateInstallation())) {
            lesMaterielsAssures.add(unMateriel);
            System.out.println("Matériel ajouté : " + unMateriel.getNumSerie());
        } else {
            System.out.println("Matériel non ajouté. La date de signature doit être antérieure à la date d'installation.");
        }
    }

}