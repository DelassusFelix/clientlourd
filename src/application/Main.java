package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnexionDB.getConnection();
        if (connection != null) {
            try {
                // Création de l'objet Statement
                Statement statement = connection.createStatement();

                // Exécution de la requête SQL
                String query = "SELECT * FROM client";
                ResultSet resultSet = statement.executeQuery(query);

                // Traitement des résultats
                while (resultSet.next()) {
                    // Récupération des données de chaque ligne
                    int id = resultSet.getInt("num_client");
                    String nom = resultSet.getString("raison_sociale");

                    // Faire quelque chose avec les données récupérées
                    System.out.println("ID : " + id + ", Nom : " + nom);
                }

                // Fermeture des ressources
                resultSet.close();
                statement.close();
                ConnexionDB.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            }
        }
    }
}
