package application;

import java.sql.SQLException;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.xml.sax.*;
import javax.xml.parsers.*;
import java.io.*;

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
        xml += "<!DOCTYPE listeMateriel SYSTEM \"materiels.dtd\">\n";
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
        xml += "</materiels>\n";
        xml += "</listeMateriel>";

        return xml;
    }

    public static boolean validateXmlAgainstDtd(String xmlContent) {
        try {
            // Configurer le DocumentBuilderFactory pour valider avec DTD
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(true);

            // Créer le DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Gestionnaire d'erreurs qui lève des exceptions en cas d'erreur
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) {
                    System.err.println("Warning: " + exception.getMessage());
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    System.err.println("Error: " + exception.getMessage());
                    throw exception; // Lever une exception pour stopper la validation
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    System.err.println("Fatal Error: " + exception.getMessage());
                    throw exception; // Lever une exception pour stopper la validation
                }
            });

            // Analyser le contenu XML
            ByteArrayInputStream input = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));
            builder.parse(input);

            // Si aucun exception n'est levée, le XML est valide
            return true;

        } catch (SAXException | ParserConfigurationException | IOException e) {
            // Si une exception est levée, le XML n'est pas valide
            System.err.println("Validation failed: " + e.getMessage());
            return false;
        }
    }


}
