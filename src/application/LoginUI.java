package application;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;

class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginUI() {
        setTitle("Connexion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Nom d'utilisateur:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Se connecter");
        panel.add(new JPanel()); // Espace pour alignement
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    handleLogin();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static boolean authenticate(String username, String password) throws SQLException {
        PersistanceSQL connexion = new PersistanceSQL();
        HashMap<String, String> users = connexion.getUsers();
        return users.containsKey(username) && BCrypt.checkpw(password, users.get(username));

    }

    private void handleLogin() throws SQLException {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (authenticate(username, password)) {
            // Authentification réussie
            SwingUtilities.invokeLater(() -> {
                try {
                    new InterfaceGraphique().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            dispose();
        } else {
            // Authentification échouée
            JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe incorrect.");
        }
    }
}
