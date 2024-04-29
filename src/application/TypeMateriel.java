package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TypeMateriel {

    private String referenceInterne, libelleTypeMateriel;
    private Famille laFamille;

    public String getReferenceInterne() {
        return referenceInterne;
    }

    public void setReferenceInterne(String referenceInterne) {
        this.referenceInterne = referenceInterne;
    }

    public String getLibelleTypeMateriel() {
        return libelleTypeMateriel;
    }

    public void setLibelleTypeMateriel(String libelleTypeMateriel) {
        this.libelleTypeMateriel = libelleTypeMateriel;
    }

    public Famille getLaFamille() {
        return laFamille;
    }

    public void setLaFamille(Famille laFamille) {
        this.laFamille = laFamille;
    }


    public TypeMateriel(String referenceInterne, String libelleTypeMateriel, Famille laFamille) {
        this.referenceInterne = referenceInterne;
        this.libelleTypeMateriel = libelleTypeMateriel;
        this.laFamille = laFamille;
    }
}