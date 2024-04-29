package application;

import java.sql.*;
import java.util.ArrayList;

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

    /*public void RangerDansBase(Object unObjet) {
        // Implémentez ici la logique pour stocker l'objet dans la base de données
        try {
            // Exemple : insérer des données dans une table (ajuster selon votre schéma)
            Statement statement = connection.prepareStatement("INSERT INTO table (champ1, champ2) VALUES (?, ?)");
            // Définissez les valeurs à partir des propriétés de l'objet
            statement.setString(1, "valeur1");
            statement.setString(2, "valeur2");
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du stockage dans la base de données", e);
        }
    }*/

    public Object ChargerDepuisBase(String id, String nomClasse) {
        String nomTable = "";
        String clePrimaire = "";
        switch (nomClasse) {
            case "Client" -> {
                nomTable = "client";
                clePrimaire = "id_client";
            }
            case "ContratMaintenance" -> {
                nomTable = "contrat_maintenance";
                clePrimaire = "num_contrat";
            }
            case "Famille" -> {
                nomTable = "famille";
                clePrimaire = "id";
            }
            case "Materiel" -> {
                nomTable = "materiel";
                clePrimaire = "num_serie";
            }
            case "TypeMateriel" -> {
                nomTable = "type";
                clePrimaire = "ref_interne";
            }
        }

        try {
            // Exemple : sélectionnez des données en fonction de l'ID et de la classe (ajuster selon votre schéma)
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + nomTable + " WHERE " + clePrimaire + " = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {

                // Création d'un objet Famille
                if (nomClasse.toLowerCase() == "famille") {
                    // récupération des informations de la famille à créer
                    int idFamille = resultSet.getInt("id");
                    String libelleFamille = resultSet.getString("libelle");

                    // création de la famille
                    Famille laFamille = new Famille(idFamille, libelleFamille);
                    return laFamille;
                }

                // Création d'un objet TypeMateriel
                if (nomClasse.toLowerCase() == "typemateriel") {
                    // récupération des informations du type à créer
                    String referenceInterne = resultSet.getString("ref_interne");
                    String libelleType = resultSet.getString("libelle");

                    // récupération de l'id de la famille associé au type
                    String idFamille = String.valueOf(resultSet.getInt("famille"));
                    // création de la famille associée au type
                    Famille laFamille = (Famille) ChargerDepuisBase(idFamille, "Famille");

                    // création du type
                    TypeMateriel leType = new TypeMateriel(referenceInterne, libelleType, laFamille);
                    return leType;
                 }

                // Création d'un objet Materiel
                if (nomClasse.toLowerCase() == "materiel") {
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
                if (nomClasse.toLowerCase() == "contratmaintenance") {
                    // récupération des infos du contrat à créer
                    int numContrat = resultSet.getInt("num_contrat");
                    Date dateSignature = resultSet.getDate("date_signature");
                    Date dateEcheance = resultSet.getDate("date_echeance");

                    // récupération de la liste des materiels assurés
                    String queryMaterielsAssures = "SELECT * FROM materiel WHERE num_contrat = " + numContrat;
                    ResultSet resultMaterielsAssures = statement.executeQuery(queryMaterielsAssures);
                    ArrayList<Materiel> lesMaterielsAssures = new ArrayList<Materiel>();

                    while (resultMaterielsAssures.next()) {
                        String idMateriel = String.valueOf(resultMaterielsAssures.getInt("num_serie"));
                        Materiel leMaterielAssure = (Materiel) ChargerDepuisBase(idMateriel, "materiel");
                        lesMaterielsAssures.add(leMaterielAssure);
                    }

                    // création du contrat
                    ContratMaintenance leContrat = new ContratMaintenance(numContrat, dateSignature, dateEcheance, lesMaterielsAssures);
                    return leContrat;
                }

                // création d'un objet Client
                if (nomClasse.toLowerCase() == "client") {
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
                        String idMateriel = String.valueOf(resultMaterielsClient.getInt("num_serie"));
                        Materiel leMaterielClient = (Materiel) ChargerDepuisBase(idMateriel, "materiel");
                        lesMaterielsClient.add(leMaterielClient);
                    }

                    // récupération du contrat associé au client
                    String queryContrat = "SELECT * FROM contrat_maintenance WHERE num_client = " + numClient;
                    ResultSet resultContrat = statement.executeQuery(queryContrat);

                    ContratMaintenance leContratClient = null;
                    while (resultContrat.next()) {
                        String numContrat = String.valueOf(resultContrat.getInt("num_contrat"));
                        leContratClient = (ContratMaintenance) ChargerDepuisBase(numContrat, "contratMaintenance");
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












    public Object ChargerDepuisBase(int id, String nomClasse) {
        String nomTable = "";
        String clePrimaire = "";
        switch (nomClasse) {
            case "Client" -> {
                nomTable = "client";
                clePrimaire = "id_client";
            }
            case "ContratMaintenance" -> {
                nomTable = "contrat_maintenance";
                clePrimaire = "num_contrat";
            }
            case "Famille" -> {
                nomTable = "famille";
                clePrimaire = "id";
            }
            case "Materiel" -> {
                nomTable = "materiel";
                clePrimaire = "num_serie";
            }
            case "TypeMateriel" -> {
                nomTable = "type";
                clePrimaire = "ref_interne";
            }
        }

        try {
            // Exemple : sélectionnez des données en fonction de l'ID et de la classe (ajuster selon votre schéma)
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + nomTable + " WHERE " + clePrimaire + " = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {

                // Création d'un objet Famille
                if (nomClasse.toLowerCase() == "famille") {
                    // récupération des informations de la famille à créer
                    int idFamille = resultSet.getInt("id");
                    String libelleFamille = resultSet.getString("libelle");

                    // création de la famille
                    Famille laFamille = new Famille(idFamille, libelleFamille);
                    return laFamille;
                }

                // Création d'un objet TypeMateriel
                if (nomClasse.toLowerCase() == "typemateriel") {
                    // récupération des informations du type à créer
                    String referenceInterne = resultSet.getString("ref_interne");
                    String libelleType = resultSet.getString("libelle");

                    // récupération de l'id de la famille associé au type
                    int idFamille = resultSet.getInt("famille");
                    // création de la famille associée au type
                    Famille laFamille = (Famille) ChargerDepuisBase(idFamille, "Famille");

                    // création du type
                    TypeMateriel leType = new TypeMateriel(referenceInterne, libelleType, laFamille);
                    return leType;
                }

                // Création d'un objet Materiel
                if (nomClasse.toLowerCase() == "materiel") {
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
                if (nomClasse.toLowerCase() == "contratmaintenance") {
                    // récupération des infos du contrat à créer
                    int numContrat = resultSet.getInt("num_contrat");
                    Date dateSignature = resultSet.getDate("date_signature");
                    Date dateEcheance = resultSet.getDate("date_echeance");

                    // récupération de la liste des materiels assurés
                    String queryMaterielsAssures = "SELECT * FROM materiel WHERE num_contrat = " + numContrat;
                    ResultSet resultMaterielsAssures = statement.executeQuery(queryMaterielsAssures);
                    ArrayList<Materiel> lesMaterielsAssures = new ArrayList<Materiel>();

                    while (resultMaterielsAssures.next()) {
                        String idMateriel = String.valueOf(resultMaterielsAssures.getInt("num_serie"));
                        Materiel leMaterielAssure = (Materiel) ChargerDepuisBase(idMateriel, "materiel");
                        lesMaterielsAssures.add(leMaterielAssure);
                    }

                    // création du contrat
                    ContratMaintenance leContrat = new ContratMaintenance(numContrat, dateSignature, dateEcheance, lesMaterielsAssures);
                    return leContrat;
                }

                // création d'un objet Client
                if (nomClasse.toLowerCase() == "client") {
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
                        String idMateriel = String.valueOf(resultMaterielsClient.getInt("num_serie"));
                        Materiel leMaterielClient = (Materiel) ChargerDepuisBase(idMateriel, "materiel");
                        lesMaterielsClient.add(leMaterielClient);
                    }

                    // récupération du contrat associé au client
                    String queryContrat = "SELECT * FROM contrat_maintenance WHERE num_client = " + numClient;
                    ResultSet resultContrat = statement.executeQuery(queryContrat);

                    ContratMaintenance leContratClient = null;
                    while (resultContrat.next()) {
                        String numContrat = String.valueOf(resultContrat.getInt("num_contrat"));
                        leContratClient = (ContratMaintenance) ChargerDepuisBase(numContrat, "contratMaintenance");
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



}
