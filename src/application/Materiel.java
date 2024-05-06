package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Materiel {

    private int numSerie;
    private Date dateVente, dateInstallation;
    private float prixVente;
    private String emplacement;
    private TypeMateriel leType;

    public int getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(int numSerie) {
        this.numSerie = numSerie;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public Date getDateInstallation() { return dateInstallation; }

    public void setDateInstallation(Date dateInstallation) { this.dateInstallation = dateInstallation; }

    public float getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(float prixVente) {
        this.prixVente = prixVente;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public TypeMateriel getLeType() {
        return leType;
    }

    public void setLeType(TypeMateriel leType) {
        this.leType = leType;
    }


    public Materiel(int numSerie, Date dateVente, Date dateInstallation, float prixVente, String emplacement, TypeMateriel leType) {
        this.numSerie = numSerie;
        this.dateVente = dateVente;
        this.dateInstallation = dateInstallation;
        this.prixVente = prixVente;
        this.emplacement = emplacement;
        this.leType = leType;
    }

    public String xmlMateriel(ContratMaintenance leContrat) {

        String chaine = "";
        chaine += "<materiel numSerie='" + this.numSerie + "' >" + '\n';
        chaine += "<type refInterne='" + this.leType.getReferenceInterne() + "' libelle ='" + this.leType.getLibelleTypeMateriel() + "' />" + '\n' + '\n';
        chaine += "<famille codeFamille='" + this.leType.getLaFamille().getCodeFamille() + "' libelle='" + this.leType.getLaFamille().getLibelleFamille() + "' />\n";
        chaine += "<date_vente>" + this.dateVente + "</date_vente>" + '\n';
        chaine += "<date_installation>" + this.dateInstallation + "</date_installation>" + '\n';
        chaine += "<prix_vente>" + this.prixVente + "</prix_vente>" + '\n';
        chaine += "<emplacement>" + this.emplacement + "</emplacement>" + '\n';
        chaine += "<nbJourAvantEcheance>" + leContrat.getJoursRestants() + "</nbJourAvantEcheance>" + '\n';
        chaine += "</materiel>\n";

        return chaine;
    }

    public String xmlMateriel() {

        String chaine = "";
        chaine += "<materiel numSerie='" + this.numSerie + "' >" + '\n';
        chaine += "<type refInterne='" + this.leType.getReferenceInterne() + "' libelle ='" + this.leType.getLibelleTypeMateriel() + "' />" + '\n' + '\n';
        chaine += "<famille codeFamille='" + this.leType.getLaFamille().getCodeFamille() + "' libelle='" + this.leType.getLaFamille().getLibelleFamille() + "' />\n";
        chaine += "<date_vente>" + this.dateVente + "</date_vente>" + '\n';
        chaine += "<date_installation>" + this.dateInstallation + "</date_installation>" + '\n';
        chaine += "<prix_vente>" + this.prixVente + "</prix_vente>" + '\n';
        chaine += "<emplacement>" + this.emplacement + "</emplacement>" + '\n';
        chaine += "</materiel>\n";

        return chaine;
    }

    }
