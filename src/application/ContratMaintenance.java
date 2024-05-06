package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        if (dateEcheance.compareTo(dateSignature) >= 0) {
            return true;
        }
        else {
            return false;
        }
    }


    public int getJoursRestants() {
        // Obtenir la date actuelle au format Date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); // Changer le format selon votre besoin
        String dateAujdCalcul = dateFormat.format(new Date());

        // Obtenir la date d'échéance sous forme de chaîne
        String dateEcheanceCalcul = dateFormat.format(this.dateEcheance);

        try {
            // Convertir les chaînes de caractères en objets Date
            Date dateAujd = dateFormat.parse(dateAujdCalcul);
            Date dateEcheance = dateFormat.parse(dateEcheanceCalcul);

            // Convertir les objets Date en LocalDate
            LocalDate localDateAujd = LocalDate.ofInstant(dateAujd.toInstant(), java.time.ZoneId.systemDefault());
            LocalDate localDateEcheance = LocalDate.ofInstant(dateEcheance.toInstant(), java.time.ZoneId.systemDefault());

            //System.out.println(localDateAujd.toString());
            //System.out.println(localDateEcheance.toString());

            // Calculer le nombre de jours entre les deux dates
            long joursEntre = ChronoUnit.DAYS.between(localDateAujd, localDateEcheance);
            return (int) joursEntre; // Convertir en int, car nous voulons un nombre entier de jours
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // En cas d'erreur de parsing
        }
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