package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TypeMateriel {

    private String referenceInterne, libelleTypeMateriel;
    private Famille laFamille;

    public String getReferenceInterne() {
        return referenceInterne;
    }

    public void setReferenceInterne(String referenceInterne) {
        this.referenceInterne = referenceInterne;
    }

    public String getLibelleTypeMateriel() {
        return libelleTypeMateriel;
    }

    public void setLibelleTypeMateriel(String libelleTypeMateriel) {
        this.libelleTypeMateriel = libelleTypeMateriel;
    }

    public Famille getLaFamille() {
        return laFamille;
    }

    public void setLaFamille(Famille laFamille) {
        this.laFamille = laFamille;
    }


    public TypeMateriel(String referenceInterne, Famille laFamille) {

        Connection connection = PersistanceSQL.getConnection();
        if (connection != null) {
            try {
                // Création de l'objet Statement
                Statement statement = connection.createStatement();

                // Exécution de la requête SQL
                String query = "SELECT * FROM type WHERE  ref_interne = " + referenceInterne;
                ResultSet resultSet = statement.executeQuery(query);

                this.referenceInterne = resultSet.getString("ref_interne");
                this.libelleTypeMateriel = resultSet.getString("libelle");
                this.laFamille = laFamille;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}