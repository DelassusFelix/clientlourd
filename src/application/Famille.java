package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Famille {

    private int codeFamille;
    private String libelleFamille;


    public int getCodeFamille() {
        return codeFamille;
    }

    public void setCodeFamille(int codeFamille) {
        this.codeFamille = codeFamille;
    }

    public String getLibelleFamille() {
        return libelleFamille;
    }

    public void setLibelleFamille(String libelleFamille) {
        this.libelleFamille = libelleFamille;
    }

    public Famille(int codeFamille, String libelleFamille) {
        this.codeFamille = codeFamille;
        this.libelleFamille = libelleFamille;
    }
}