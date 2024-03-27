package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PersistanceSQL {
    // Informations de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/cashcash";
    private static final String USER = "root";
    private static final String PASSWORD = "YDmKqC4HIAWuYORZzFX1";

    // Méthode pour établir la connexion à la base de données
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Enregistrement du pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Etablissement de la connexion
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie !");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement du pilote JDBC : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
        return connection;
    }

    // Méthode pour fermer la connexion à la base de données
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connexion à la base de données fermée avec succès !");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }

    // Exemple d'utilisation
    public static void main(String[] args) {
        Connection connection = getConnection();
        // Utiliser la connexion pour exécuter des requêtes, etc.
        // N'oublie pas de fermer la connexion une fois que tu as fini avec elle
        closeConnection(connection);
    }
}
