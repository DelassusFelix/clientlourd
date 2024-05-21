package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PersistanceSQL {
    private Connection connection;

    public PersistanceSQL() {
        // Construire la chaîne de connexion
        String connectionString = "jdbc:mysql://127.0.0.1:3306/cashcash";
        try {
            // Initialiser la connexion à la base de données
            connection = DriverManager.getConnection(connectionString, "root", "YDmKqC4HIAWuYORZzFX1");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la connexion à la base de données", e);
        }
    }

    public void RangerDansBase(Object unObjet) throws SQLException {
        switch(String.valueOf(unObjet.getClass())) {
            case "Famille" -> {
                String insertQuery = "INSERT INTO famille(id, libelle) VALUES (?, ?)";
                Famille laFamille = (Famille) unObjet;
                try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                    // Définition des valeurs des paramètres
                    statement.setString(1, laFamille.getCodeFamille());
                    statement.setString(2, laFamille.getLibelleFamille());
                    statement.executeUpdate();
                }
            }

            case "TypeMateriel" -> {
                String insertQuery = "INSERT INTO type(ref_interne, libelle, famille) VALUES (?, ?, ?)";
                TypeMateriel leType = (TypeMateriel) unObjet;
                try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                    // Définition des valeurs des paramètres
                    statement.setString(1, leType.getReferenceInterne());
                    statement.setString(2, leType.getLibelleTypeMateriel());
                    statement.setString(3, leType.getLaFamille().getCodeFamille());
                    statement.executeUpdate();
                }
            }
/*
            case "Client" -> {
                Client leClient = (Client) unObjet;
                int numContrat = 0;
                if(leClient.getLeContrat() != null) {
                    numContrat = leClient.getLeContrat().getNumContrat();
                }

                String insertClientQuery = "INSERT INTO client(num_client, raison_sociale, num_siren, code_ape, adresse, num_tel, dist_agence_km, duree_moy_deplacement, num_agence) VALUES (?, ?, ?)";
                String insertContratQuery = "INSERT INTO contrat_maintenance(num_contrat, date_1ere_sign, date_signature, date_echeance, num_client) VALUES (?, ?, ?, ?, ?)";
                String insertMaterielsQuery = "INSERT INTO materiel(ref_interne, libelle, famille) VALUES (?, ?, ?)";
                TypeMateriel leType = (TypeMateriel) unObjet;

                for(Materiel leMateriel : leClient.getLesMateriels()) {
                    try (PreparedStatement statement = connection.prepareStatement(insertContratQuery)) {
                        // Définition des valeurs des paramètres
                        statement.setString(1, );
                        statement.setString(2, );
                        statement.executeUpdate();
                    }
                }

                try (PreparedStatement statement = connection.prepareStatement(insertContratQuery)) {
                    // Définition des valeurs des paramètres
                    statement.setString(1, );
                    statement.setString(2, );
                    statement.executeUpdate();
                }

                try (PreparedStatement statement = connection.prepareStatement(insertClientQuery)) {
                    // Définition des valeurs des paramètres
                    statement.setString(1, );
                    statement.setString(2, );
                    statement.executeUpdate();
                }
            }*/
        }
    }

    public void updateMateriel(int numSerie, int numContrat) throws SQLException {
        String updateQuery = "UPDATE materiel SET num_contrat = ? WHERE num_serie = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            // Définition des valeurs des paramètres
            statement.setInt(1, numContrat); // Pour num_contrat
            statement.setInt(2, numSerie);   // Pour num_serie

            // Exécute la requête de mise à jour
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Mise à jour réussie!");
            } else {
                System.out.println("Aucune ligne mise à jour.");
            }
        }
    }

    public Object ChargerDepuisBase(String id, String nomClasse) {
        String nomTable = "";
        String clePrimaire = "";
        switch (nomClasse) {

            case "TypeMateriel" -> {
                nomTable = String.valueOf("type");
                clePrimaire = "ref_interne";
            }
            case "Famille" -> {
                nomTable = "famille";
                clePrimaire = "id";
            }
        }

        try {
            // Exemple : sélectionnez des données en fonction de l'ID et de la classe (ajuster selon votre schéma)
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + nomTable + " WHERE " + clePrimaire + " = \'" + id + "\'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {

                // Création d'un objet Famille
                if (nomClasse == "Famille") {
                    // récupération des informations de la famille à créer
                    String idFamille = resultSet.getString("id");
                    String libelleFamille = resultSet.getString("libelle");

                    // création de la famille
                    Famille laFamille = new Famille(idFamille, libelleFamille);
                    return laFamille;
                }

                // Création d'un objet TypeMateriel
                if (nomClasse == "TypeMateriel") {
                    // récupération des informations du type à créer
                    String referenceInterne = resultSet.getString("ref_interne");
                    String libelleType = resultSet.getString("libelle");

                    // récupération de l'id de la famille associé au type
                    String idFamille = resultSet.getString("famille");
                    // création de la famille associée au type
                    Famille laFamille = (Famille) ChargerDepuisBase(idFamille, "Famille");

                    // création du type
                    TypeMateriel leType = new TypeMateriel(referenceInterne, libelleType, laFamille);
                    return leType;
                 }

                else {
                    return null;
                }

            } else {
                throw new RuntimeException("Aucun objet trouvé pour cet identifiant");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement depuis la base de données", e);
        }
    }


    public Object ChargerDepuisBase(int id, String nomClasse) {
        String nomTable = "";
        String clePrimaire = "";
        switch (nomClasse) {
            case "Client" -> {
                nomTable = "client";
                clePrimaire = "num_client";
            }
            case "ContratMaintenance" -> {
                nomTable = "contrat_maintenance";
                clePrimaire = "num_contrat";
            }
            case "Materiel" -> {
                nomTable = "materiel";
                clePrimaire = "num_serie";
            }

        }

        try {
            // Exemple : sélectionnez des données en fonction de l'ID et de la classe (ajuster selon votre schéma)
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + nomTable + " WHERE " + clePrimaire + " = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {

                // Création d'un objet Materiel
                if (nomClasse == "Materiel") {
                    // récupération des infos du materiel à créer
                    int numSerie = resultSet.getInt("num_serie");
                    Date dateVente = resultSet.getDate("date_vente");
                    Date dateInstallation = resultSet.getDate("date_installation");
                    float prixVente = resultSet.getFloat("prix_vente");
                    String emplacement = resultSet.getString("emplacement");

                    // récupération de l'id du type associé au materiel
                    String idType = resultSet.getString("ref_interne");
                    // création du type associé au materiel
                    TypeMateriel leType = (TypeMateriel) ChargerDepuisBase(idType, "TypeMateriel");

                    // création du materiel
                    Materiel leMateriel = new Materiel(numSerie, dateVente, dateInstallation, prixVente, emplacement, leType);
                    return leMateriel;
                }

                //création d'un objet ContratMaintenance
                if (nomClasse == "ContratMaintenance") {
                    // récupération des infos du contrat à créer
                    int numContrat = resultSet.getInt("num_contrat");
                    Date dateSignature = resultSet.getDate("date_signature");
                    Date dateEcheance = resultSet.getDate("date_echeance");

                    // récupération de la liste des materiels assurés
                    String queryMaterielsAssures = "SELECT * FROM materiel WHERE num_contrat = " + numContrat;
                    ResultSet resultMaterielsAssures = statement.executeQuery(queryMaterielsAssures);
                    ArrayList<Materiel> lesMaterielsAssures = new ArrayList<Materiel>();

                    while (resultMaterielsAssures.next()) {
                        int idMateriel = resultMaterielsAssures.getInt("num_serie");
                        Materiel leMaterielAssure = (Materiel) ChargerDepuisBase(idMateriel, "Materiel");
                        lesMaterielsAssures.add(leMaterielAssure);
                    }

                    // création du contrat
                    ContratMaintenance leContrat = new ContratMaintenance(numContrat, dateSignature, dateEcheance, lesMaterielsAssures);
                    return leContrat;
                }

                // création d'un objet Client
                if (nomClasse == "Client") {
                    // récupération des infos du client à créer
                    int numClient = resultSet.getInt("num_client");
                    String raisonSociale = resultSet.getString("raison_sociale");
                    String siren = resultSet.getString("num_siren");
                    String codeApe = resultSet.getString("code_ape");
                    String adresse = resultSet.getString("adresse") + ", " + resultSet.getString("code_postal") + " " + resultSet.getString("ville").toUpperCase();
                    String telephone = resultSet.getString("num_tel");
                    String email = resultSet.getString("courriel");
                    int dureeDeplacement = resultSet.getInt("duree_moy_deplacement");
                    int distanceKm = resultSet.getInt("dist_agence_km");

                    // récupération de la liste des materiels du client
                    String queryMaterielsClient = "SELECT * FROM materiel WHERE num_client = " + numClient;
                    ResultSet resultMaterielsClient = statement.executeQuery(queryMaterielsClient);
                    ArrayList<Materiel> lesMaterielsClient = new ArrayList<Materiel>();

                    while (resultMaterielsClient.next()) {
                        int idMateriel = resultMaterielsClient.getInt("num_serie");
                        Materiel leMaterielClient = (Materiel) ChargerDepuisBase(idMateriel, "Materiel");
                        lesMaterielsClient.add(leMaterielClient);
                    }

                    // récupération du contrat associé au client
                    String queryContrat = "SELECT * FROM contrat_maintenance WHERE num_client = " + numClient;
                    ResultSet resultContrat = statement.executeQuery(queryContrat);

                    ContratMaintenance leContratClient = null;
                    while (resultContrat.next()) {
                        int numContrat = resultContrat.getInt("num_contrat");
                        leContratClient = (ContratMaintenance) ChargerDepuisBase(numContrat, "ContratMaintenance");
                    }

                    // Création du client
                    Client leClient = new Client(numClient, raisonSociale, siren, codeApe, adresse, telephone, email, dureeDeplacement, distanceKm, lesMaterielsClient, leContratClient);
                    return leClient;
                }

                else {
                    return null;
                }

            } else {
                throw new RuntimeException("Aucun objet trouvé pour cet identifiant");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement depuis la base de données", e);
        }
    }


    public HashMap getUsers() throws SQLException {

        HashMap<String, String> users = new HashMap<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM assistant";
        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()){
            users.put(resultSet.getString("matricule"), resultSet.getString("passwordClientLourd"));
        }
        return users;
    }

    public ArrayList<Client> getClients() throws SQLException {

        ArrayList<Client> lesClients = new ArrayList<>();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM client ORDER BY num_client ASC";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()){
           Client leClient = (Client) ChargerDepuisBase(resultSet.getInt("num_client"),"Client");
           lesClients.add(leClient);
        }
        return lesClients;
    }
}
