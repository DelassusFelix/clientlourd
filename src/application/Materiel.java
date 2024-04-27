package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Materiel {

    private int numSerie;
    private Date dateVente, dateInstallation;
    private float prixVente;
    private String emplacement;
    private TypeMateriel leType;

    public int getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(int numSerie) {
        this.numSerie = numSerie;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public Date getDateInstallation() { return dateInstallation; }

    public void setDateInstallation(Date dateInstallation) { this.dateInstallation = dateInstallation; }

    public float getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(float prixVente) {
        this.prixVente = prixVente;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public TypeMateriel getLeType() {
        return leType;
    }

    public void setLeType(TypeMateriel leType) {
        this.leType = leType;
    }



    public Materiel(int numSerie, TypeMateriel leType) {

        Connection connection = PersistanceSQL.getConnection();
        if (connection != null) {
            try {
                // Création de l'objet Statement
                Statement statement = connection.createStatement();

                // Exécution de la requête SQL
                String query = "SELECT * FROM materiel WHERE  num_serie = " + numSerie;
                ResultSet resultSet = statement.executeQuery(query);

                this.numSerie = numSerie;
                this.dateVente = resultSet.getDate("date_vente");
                this.prixVente = resultSet.getFloat("prix_vente");
                this.dateInstallation = resultSet.getDate("date_installation");
                this.emplacement = resultSet.getString("emplacement");
                this.leType = leType;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }


    public String xmlMateriel() {
        String chaine = "";
        chaine += "<materiel numSerie='" + this.numSerie + "' >" + '\n';
        chaine += "<type refInterne='" + this.leType.getReferenceInterne() + "' libelle ='" + this.leType.getLibelleTypeMateriel() + "' />" + '\n';
        chaine += "<date_vente>" + this.dateVente + "</date_vente>" + '\n';
        chaine += "<date_installation>" + this.dateInstallation + "</date_installation>" + '\n';
        chaine += "<prix_vente>" + this.prixVente + "</prix_vente>" + '\n';
        chaine += "<emplacement>" + this.emplacement + "</emplacement>" + '\n';
        chaine += "<nbJourAvantEcheance>" +  + "</nbJourAvantEcheance>";

        return chaine;
    }

    }
