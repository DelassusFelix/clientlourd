package application;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InterfaceGraphique extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private PersistanceSQL donnees = new PersistanceSQL();
    private GestionMateriels gestionClients = new GestionMateriels(donnees);
    private ArrayList<Client> clientList = donnees.getClients();
    private List<JCheckBox> checkBoxList;

    public InterfaceGraphique() throws SQLException {
        setTitle("CashCash gestion materiels");
        setSize(854, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel panneauPrincipal = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20); // Champ de recherche
        JButton searchButton = new JButton("Rechercher");

        JPanel resultsPanel = new JPanel(); // Conteneur pour les résultats
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS)); // Afficher les résultats verticalement

        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            List<Client> results = clientList.stream()
                    .filter(client -> client.getRaisonSociale().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());

            // Effacer les anciens résultats
            resultsPanel.removeAll();

            Border separator = new LineBorder(Color.GRAY, 1, false);

            if (results.isEmpty()) {
                resultsPanel.add(new JLabel("Aucun résultat trouvé."));
            } else {
                for (Client client : results) {
                    JPanel clientPanel = new JPanel(new BorderLayout()); // Panneau pour chaque client
                    JLabel clientLabel = new JLabel(client.getRaisonSociale());
                    JButton gererContratButton = new JButton("Gérer contrat");
                    JButton generateXMLButton = new JButton("Générer XML");

                    // Action pour le bouton "Générer XML"
                    generateXMLButton.addActionListener(evt -> {
                        // Action pour générer le fichier XML pour ce client
                        JOptionPane.showMessageDialog(
                                this,
                                "Génération du XML pour le client : " + client.getRaisonSociale()
                        );
                        try {
                            String nomFichier = "materielClient" + client.getNumClient() + ".xml";
                            Fichier fichier = new Fichier();
                            fichier.ouvrir(nomFichier, "W");
                            String xml = gestionClients.XmlClient(client);
                            if(gestionClients.validateXmlAgainstDtd(xml)){
                                fichier.ecrire(xml);
                                JOptionPane.showMessageDialog(
                                        this,
                                        "Génération du fichier réussie !"
                                );
                            }else {
                                JOptionPane.showMessageDialog(
                                        this,
                                        "Erreur lors de la génération du fichier."
                                );
                            }
                            fichier.fermer();


                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Erreur lors de la génération du fichier."
                            );
                            throw new RuntimeException(ex);

                        }
                    });


                    // Action pour le bouton "Générer XML"
                    gererContratButton.addActionListener(evt -> {
                        if (!client.estAssure()) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Le client " + client.getRaisonSociale() + " ne possède pas de contrat."
                            );
                        } else {
                            JPanel materielsPanel = new JPanel(); // Nouveau panneau
                            materielsPanel.setLayout(new BoxLayout(materielsPanel, BoxLayout.Y_AXIS)); // Disposition verticale

                            checkBoxList = new ArrayList<>();

                            JLabel title = new JLabel("Ajouter des materiels au contrat");
                            title.setFont(title.getFont().deriveFont(20f));
                            title.setBorder(new EmptyBorder(10, 0, 15, 0));
                            materielsPanel.add(title, BorderLayout.NORTH);

                            // Pour chaque matériel hors contrat, ajouter une checkbox
                            for (Materiel materiel : client.getMaterielsHorsContrat()) {
                                JCheckBox materielCheckBox = new JCheckBox(
                                        String.valueOf(materiel.getNumSerie())
                                );
                                JLabel description = new JLabel(" - " + materiel.getLeType().getLibelleTypeMateriel());
                                description.setBorder(new EmptyBorder(0, 50, 0, 0));
                                materielCheckBox.add(description);

                                checkBoxList.add(materielCheckBox); // Garder une référence
                                materielsPanel.add(materielCheckBox); // Ajouter au panneau
                            }

                            JButton updateMaterielButton = new JButton("Ajouter les matériels au contrat");
                            updateMaterielButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    List<String> checkedItems = getCheckedItems(); // Obtenez les cases cochées
                                    for(String numSerie : checkedItems){
                                        try {
                                            donnees.updateMateriel(Integer.valueOf(numSerie), client.getLeContrat().getNumContrat());
                                            client.getLeContrat().ajouteMateriel((Materiel) donnees.ChargerDepuisBase(Integer.valueOf(numSerie), "Materiel"));
                                        } catch (SQLException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Materiels ajoutés au contrat : " + String.join(", ", checkedItems),
                                            "Résultat",
                                            JOptionPane.INFORMATION_MESSAGE
                                    );
                                    cardLayout.show(mainPanel, "CashCash");
                                }
                            });

                            // Ajout de marge ou d'espace si nécessaire
                            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                            buttonPanel.add(updateMaterielButton); // Ajoute le bouton à un conteneur distinct

                            materielsPanel.add(buttonPanel); // Ajoute le panneau des boutons au panneau principal

                            // Ajoute le panneau des matériels au CardLayout
                            mainPanel.add(new JScrollPane(materielsPanel), "MaterielsClient");

                            materielsPanel.revalidate();
                            materielsPanel.repaint();

                            // Afficher la carte avec les matériels
                            cardLayout.show(mainPanel, "MaterielsClient");
                        }
                    });


                    // Crée un sous-panneau avec un FlowLayout pour les boutons
                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    buttonPanel.add(generateXMLButton);
                    if (client.estAssure()){
                        buttonPanel.add(gererContratButton);
                    }

                    clientPanel.setBorder(separator);
                    clientPanel.add(clientLabel, BorderLayout.WEST);
                    clientPanel.add(buttonPanel, BorderLayout.EAST);
                    resultsPanel.add(clientPanel); // Ajouter à la liste des résultats
                }
            }

            // Rafraîchir le panneau des résultats
            resultsPanel.revalidate();
            resultsPanel.repaint();
        });

        searchPanel.add(new JLabel("Rechercher un client:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        panneauPrincipal.add(searchPanel, BorderLayout.NORTH); // Barre de recherche en haut
        panneauPrincipal.add(new JScrollPane(resultsPanel), BorderLayout.CENTER); // Résultats au centre


        mainPanel.add(panneauPrincipal, "CashCash");

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Ajout d'un menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Menu");
        menuBar.add(fileMenu);

        JMenuItem indexMenu = new JMenuItem("Rechercher client");
        indexMenu.addActionListener(e -> cardLayout.show(mainPanel, "CashCash"));
        fileMenu.add(indexMenu);

        JMenuItem quitMenuItem = new JMenuItem("Quitter");
        quitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(quitMenuItem);

        setLocationRelativeTo(null); // Centre la fenêtre
    }


    public List<String> getCheckedItems() {
        List<String> checkedItems = new ArrayList<>();
        for (JCheckBox checkBox : checkBoxList) {
            if (checkBox.isSelected()) {
                checkedItems.add(checkBox.getText()); // Récupérez le texte des cases cochées
            }
        }
        return checkedItems; // Retournez la liste des cases cochées
    }

    public void createPdfRelance() throws IOException, SQLException {
        File folder = new File("stockPdfRelance");
        FolderCleaner.clearFolder(folder);

        ArrayList<Client> allClients = donnees.getClients();
        Fichier fichier = new Fichier();
        for (Client unClient : allClients) {
            if(unClient.getLeContrat() != null && unClient.getLeContrat().getJoursRestants() <= 31){
                fichier.generePdf(unClient);
            }
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new InterfaceGraphique().setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
