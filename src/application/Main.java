package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        PersistanceSQL connection = new PersistanceSQL();
        Client leClient = (Client) connection.ChargerDepuisBase(1, "Client");
        System.out.println(leClient.getRaisonSociale());



        //ContratMaintenance leContrat = (ContratMaintenance) connection.ChargerDepuisBase(10, "ContratMaintenance");
        //System.out.println(leContrat.getDateSignature());

    }


}
