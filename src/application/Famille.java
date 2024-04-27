package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Famille {

    private String libelleFamille;
    private int codeFamille;

    public int getCodeFamille() {
        return codeFamille;
    }

    public void setCodeFamille(int codeFamille) {
        this.codeFamille = codeFamille;
    }

    public String getLibelleFamille() {
        return libelleFamille;
    }

    public void setLibelleFamille(String libelleFamille) {
        this.libelleFamille = libelleFamille;
    }


    public Famille(int idFamille) {

        Connection connection = PersistanceSQL.getConnection();
        if (connection != null) {
            try {
                // Création de l'objet Statement
                Statement statement = connection.createStatement();

                // Exécution de la requête SQL
                String query = "SELECT * FROM famille WHERE  id = " + idFamille;
                ResultSet resultSet = statement.executeQuery(query);

                this.codeFamille = resultSet.getInt("id");
                this.libelleFamille = resultSet.getString("libelle");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}