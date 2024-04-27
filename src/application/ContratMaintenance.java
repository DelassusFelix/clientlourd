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

    public ContratMaintenance(String numContrat, ArrayList<Materiel> allMateriels) {

        Connection connection = PersistanceSQL.getConnection();
        if (connection != null) {
            try {
                // Création de l'objet Statement
                Statement statement = connection.createStatement();

                // Exécution de la requête SQL
                String query = "SELECT * FROM contrat_maintenance WHERE  num_contrat = " + numContrat;
                ResultSet resultSet = statement.executeQuery(query);

                this.numContrat = resultSet.getInt("num_contrat");
                this.dateSignature = resultSet.getDate("date_signature");
                this.dateEcheance = resultSet.getDate("date_echeance");

                query = "SELECT num_serie FROM materiel WHERE  num_contrat = " + numContrat;
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    for (Materiel materiel : allMateriels) {
                        if (materiel.getNumSerie() == resultSet.getInt("num_serie")){
                            this.lesMaterielsAssures.add(materiel);
                        }
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public boolean estValide() {
        // Obtenir la date actuelle au format LocalDate
        LocalDate today = LocalDate.now();

        // Convertir dateSignature et dateEcheance en LocalDate
        LocalDate signature = this.dateSignature.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate echeance = this.dateEcheance.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Retourner true si la date actuelle est entre la date de signature et la date d'échéance
        return !today.isBefore(signature) && !today.isAfter(echeance);
    }


    public int getJoursRestants() {
        // Convertir la date d'échéance en LocalDate
        LocalDate dateEcheanceLocal = this.dateEcheance.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Obtenir la date actuelle au format LocalDate
        LocalDate today = LocalDate.now();

        // Calculer le nombre de jours entre aujourd'hui et la date d'échéance
        return (int) ChronoUnit.DAYS.between(today, dateEcheanceLocal);
    }


    public void ajouteMateriel(Materiel unMateriel) {
        // Comparer la date de signature avec la date d'installation du matériel
        if (dateSignature.before(unMateriel.getDateInstallation())) {
            lesMaterielsAssures.add(unMateriel); // Ajouter le matériel à la collection
            System.out.println("Matériel ajouté : " + unMateriel.getNumSerie());
        } else {
            System.out.println("Matériel non ajouté. La date de signature doit être antérieure à la date d'installation.");
        }
    }

}