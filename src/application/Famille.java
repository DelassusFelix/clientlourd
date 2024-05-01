package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Famille {


    private String libelleFamille, codeFamille;


    public String getCodeFamille() {
        return codeFamille;
    }

    public void setCodeFamille(String codeFamille) {
        this.codeFamille = codeFamille;
    }

    public String getLibelleFamille() {
        return libelleFamille;
    }

    public void setLibelleFamille(String libelleFamille) {
        this.libelleFamille = libelleFamille;
    }

    public Famille(String codeFamille, String libelleFamille) {
        this.codeFamille = codeFamille;
        this.libelleFamille = libelleFamille;
    }
}