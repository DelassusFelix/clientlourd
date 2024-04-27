package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Client {

    private String raisonSociale, siren, codeApe, adresse, telClient, email;
    private int numClient, dureeDeplacement, distanceKm;
    private ArrayList<Materiel> lesMateriels;
    private ContratMaintenance leContrat;

    public int getNumClient() {
        return numClient;
    }

    public void setNumClient(int numClient) {
        this.numClient = numClient;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getCodeApe() {
        return codeApe;
    }

    public void setCodeApe(String codeApe) {
        this.codeApe = codeApe;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelClient() {
        return telClient;
    }

    public void setTelClient(String telClient) {
        this.telClient = telClient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDureeDeplacement() {
        return dureeDeplacement;
    }

    public void setDureeDeplacement(int dureeDeplacement) {
        this.dureeDeplacement = dureeDeplacement;
    }

    public int getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(int distanceKm) {
        this.distanceKm = distanceKm;
    }

    public ArrayList<Materiel> getLesMateriels() {
        return lesMateriels;
    }

    public void setLesMateriels(ArrayList<Materiel> lesMateriels) {
        this.lesMateriels = lesMateriels;
    }

    public ContratMaintenance getLeContrat() {
        return leContrat;
    }

    public void setLeContrat(ContratMaintenance leContrat) {
        this.leContrat = leContrat;
    }

    public Client(int numClient, ArrayList<Materiel> allMateriels, ArrayList<ContratMaintenance> allContrats) {

        Connection connection = PersistanceSQL.getConnection();
        if (connection != null) {
            try {
                // Création de l'objet Statement
                Statement statement = connection.createStatement();

                // Exécution de la requête SQL
                String query = "SELECT * FROM client WHERE  num_client = " + numClient;
                ResultSet resultSet = statement.executeQuery(query);

                this.numClient = numClient;
                this.raisonSociale = resultSet.getString("raison_sociale");
                this.siren = resultSet.getString("num_siren");
                this.codeApe = resultSet.getString("code_ape");
                this.adresse = resultSet.getString("adresse");
                this.telClient = resultSet.getString("num_tel");
                this.email = resultSet.getString("courriel");
                this.dureeDeplacement = resultSet.getInt("duree_moy_deplacement");
                this.distanceKm = resultSet.getInt("dist_agence_km");

                query = "SELECT num_serie FROM materiel WHERE  num_client = " + numClient;
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    for (Materiel materiel : allMateriels) {
                        if (materiel.getNumSerie() == resultSet.getInt("num_serie")){
                            this.lesMateriels.add(materiel);
                        }
                    }
                }

                query = "SELECT num_contrat FROM contrat_maintenance WHERE num_client = " + numClient;
                resultSet = statement.executeQuery(query);

                for (ContratMaintenance contrat : allContrats) {
                    if (contrat.getNumContrat() == resultSet.getInt("num_contrat")){
                        this.leContrat = contrat;
                        break;
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
