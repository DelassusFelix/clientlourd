package application;

import java.util.ArrayList;

public class GestionMateriels {

    private PersistanceSQL donnees;

    public GestionMateriels(PersistanceSQL donnees) {
        this.donnees = donnees;
    }

    public Client getClient(int idClient) {
        Client leClient = (Client) this.donnees.ChargerDepuisBase(idClient, "Client");
        return leClient;
    }

    public String XmlClient(Client unClient) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        xml += "<listeMateriel>\n";
        xml += "<materiels idClient=\"" + unClient.getNumClient() + "\" >\n";
        xml += "<sousContrat>\n";

        ArrayList<Materiel> lesMaterielsAssures = unClient.getMaterielsSousContrat();
        for (Materiel materiel : lesMaterielsAssures) {
            xml += materiel.xmlMateriel(unClient.getLeContrat());
        }

        xml += "</sousContrat>\n";
        xml += "<horsContrat>\n";

        ArrayList<Materiel> lesMaterielsHorsContrat = unClient.getMaterielsHorsContrat();
        for (Materiel materiel : lesMaterielsHorsContrat) {
            xml += materiel.xmlMateriel();
        }

        xml += "</horsContrat>\n";
        xml += "</listeMateriel>";

        return xml;
    }

}
