package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Materiel {

    private int numSerie;
    private Date dateVente;
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



    public Materiel(int numSerie) {

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
                this.emplacement = resultSet.getString("emplacement");
                this.leType = leType;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
    }
