package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceGraphique extends JFrame {
    public InterfaceGraphique() {
        // Configuration de la fenêtre principale
        setTitle("CashCash XML");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.LIGHT_GRAY);

        // Ajout d'un titre
        JLabel titleLabel = new JLabel("CashCash XML", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
        getContentPane().add(titleLabel, BorderLayout.NORTH);

        // Ajout d'un menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Fonctionalités");
        menuBar.add(fileMenu);

        JMenuItem xmlMenuItem = new JMenuItem("Générer XML");
        xmlMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Page pour générer un fichier XML"));
        fileMenu.add(xmlMenuItem);

        JMenuItem ajtMaterielMenuItem = new JMenuItem("Ajouter un materiel à un contrat");
        ajtMaterielMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Page pour ajouter un materiel à un contrat"));
        fileMenu.add(ajtMaterielMenuItem);

        JMenuItem pdfMenuItem = new JMenuItem("Générer PDF de relance");
        pdfMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Page pour générer un fichier PDF de relance"));
        fileMenu.add(pdfMenuItem);

        JMenuItem quitMenuItem = new JMenuItem("Quitter");
        quitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(quitMenuItem);

        // Ajout d'une barre de recherche textuelle
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Rechercher :"));
        JTextField searchField = new JTextField(15);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText();
            JOptionPane.showMessageDialog(this, "Recherche pour : " + searchQuery);
        });

        searchPanel.add(searchButton);
        getContentPane().add(searchPanel, BorderLayout.CENTER);

        // Ajout d'un composant de sélection (menu déroulant)
        JPanel selectPanel = new JPanel(new FlowLayout());
        selectPanel.add(new JLabel("Sélection :"));

        String[] options = { "Option 1", "Option 2", "Option 3" };
        JComboBox<String> selectComboBox = new JComboBox<>(options);
        selectComboBox.addActionListener(e -> {
            String selectedOption = (String) selectComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Vous avez choisi : " + selectedOption);
        });

        selectPanel.add(selectComboBox);
        getContentPane().add(selectPanel, BorderLayout.LINE_START);
    }

}
