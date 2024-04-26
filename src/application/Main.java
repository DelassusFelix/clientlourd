package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

    }

    public void genererFichierXML(int numClient) {
        Connection connection = PersistanceSQL.getConnection();
        if (connection != null) {
            try {
                // Création de l'objet Statement
                Statement statement = connection.createStatement();

                // Exécution de la requête SQL
                String query = "SELECT materiel.*, type.libelle FROM materiel, type WHERE materiel.ref_interne = type.ref_interne AND num_client = " + numClient + " AND materiel.num_contrat IS NOT NULL";
                ResultSet resultSet = statement.executeQuery(query);

                // utilisation de la classe fichier
                Fichier f = new Fichier();

                // création du fichier "materiels.xml"
                f.ouvrir("materielsclient" + numClient + ".xml", "W");

                // écriture des données xml
                // entête
                f.ecrire("<?xml version='1.0' encoding='UTF-8'?>");
                f.ecrire("<!DOCTYPE materiels SYSTEM './materiels.dtd'>");

                // contenu du fichier xml
                f.ecrire("<materiels idClient='" + numClient + "'>");
                f.ecrire("<sousContrat>");

                // Parcours de l’ArrayList
                while (resultSet.next()) {
                    // Récupération des données de chaque ligne
                    int numContrat = resultSet.getInt("num_contrat");
                    int numSerie = resultSet.getInt("num_serie");
                    String refInterne = resultSet.getString("Ref_interne");
                    String libelle = resultSet.getString("libelle");


                        f.ecrire("<materiel numSerie=" + numSerie + ">");
                        f.ecrire("<type refInterne=" + refInterne + " libelle=" + libelle + "/>");
                        f.ecrire("<famille codeFamille="+ "FAMILLE !!!" + "libelle=" + "FAMILLE !!!" + " />");
                        f.ecrire("<date_vente>" + resultSet.getDate("date_vente") + "</date_vente>");
                        f.ecrire("<date_installation>" + resultSet.getDate("date_installation") + "</date_installation>");
                        f.ecrire("<prix_vente>" + resultSet.getInt("prix_vente") + "</prix_vente>");
                        f.ecrire("<emplacement>" + resultSet.getString("emplacement") + "</emplacement>");



                        f.ecrire("</materiel>");
                    }


                // Fin du fichier
                f.ecrire("</sousContrat>");
                f.ecrire("</materiels>");
                // fermeture du fichier
                f.fermer();

                // Fermeture des ressources
                resultSet.close();
                statement.close();
                PersistanceSQL.closeConnection(connection);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}
