package application;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        // Persistance de données et autres opérations
        PersistanceSQL connection = new PersistanceSQL();
        Client leClient = (Client) connection.ChargerDepuisBase(1, "Client");
        GestionMateriels gestion = new GestionMateriels(connection);

        String xml = gestion.XmlClient(leClient);
        Fichier fichier = new Fichier();
        fichier.ouvrir("testxml.xml", "W");
        fichier.ecrire(xml);
        fichier.fermer();

        LoginUI interfaceConnexion = new LoginUI();
        interfaceConnexion.setVisible(true);

    }


}
