package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class ShoppingView {
    private JFrame frame;
    private JDialog gifDialog;
    private JPanel mainPanel;
    private JPanel entryPanel;
    private JPanel loginPanel;
    private JPanel registerPanel;
    private CardLayout cardLayout;
    private Image icon;
    private int currentFrame = 1;
    private static final int TOTAL_FRAMES = 49;
    private Timer timer;

    public ShoppingView() {
        gifDialog = new JDialog();
        gifDialog.setUndecorated(true);
        gifDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        gifDialog.setResizable(false);

        try {
            icon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/image/logo.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        entryPanel = createEntryPanel();
        loginPanel = createLoginPanel();
        registerPanel = createRegisterPanel();

        mainPanel.add(entryPanel, "Entry");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registerPanel, "Register");

        JLabel gifLabel = new JLabel();
        gifDialog.add(gifLabel, BorderLayout.CENTER);
        updateGifLabel(gifLabel);
        gifDialog.pack();
        gifDialog.setLocationRelativeTo(null);

        timer = new Timer(100, e -> {
            if (currentFrame <= TOTAL_FRAMES) {
                updateGifLabel(gifLabel);
                currentFrame++;
            } else {
                timer.stop();
                gifDialog.dispose();
                createMainFrame();
            }
        });
        timer.start();
    }

    private void updateGifLabel(JLabel gifLabel) {
        String framePath = String.format("/gif/blog/frame-%03d.gif", currentFrame);
        gifLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(framePath))));
    }

    private void createMainFrame() {
        frame = new JFrame("Shopping App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(800, 600);
        frame.setIconImage(icon);
        frame.add(mainPanel);
        frame.setVisible(true);
        showPage("Entry");
    }

    private JPanel createEntryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Bienvenue !");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;

        JButton loginButton = createStyledButton("Connexion");
        gbc.gridx = 0;
        panel.add(loginButton, gbc);

        JButton registerButton = createStyledButton("Inscription");
        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        JButton quitButton = createStyledButton("Quitter");
        quitButton.addActionListener(e -> System.exit(0));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(quitButton, gbc);

        loginButton.addActionListener(e -> showPage("Login"));
        registerButton.addActionListener(e -> showPage("Register"));

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        panel.add(new JLabel("Email:"), gbc);
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);

        panel.add(new JLabel("Mot de passe:"), gbc);
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        JButton loginButton = createStyledButton("Connexion");
        panel.add(loginButton, gbc);

        JButton returnButton = createStyledButton("Retour");
        returnButton.addActionListener(e -> showPage("Entry"));
        panel.add(returnButton, gbc);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Champ nom
        JLabel nomLabel = new JLabel("Nom:");
        panel.add(nomLabel, gbc);
        JTextField nomField = new JTextField(20);
        panel.add(nomField, gbc);

        // Champ prénom
        JLabel prenomLabel = new JLabel("Prénom:");
        panel.add(prenomLabel, gbc);
        JTextField prenomField = new JTextField(20);
        panel.add(prenomField, gbc);

        // Champ email
        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel, gbc);
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Champ mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        panel.add(passwordLabel, gbc);
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Champ confirmation mot de passe
        JLabel confirmPasswordLabel = new JLabel("Confirmer le mot de passe:");
        panel.add(confirmPasswordLabel, gbc);
        JPasswordField confirmPasswordField = new JPasswordField(20);
        panel.add(confirmPasswordField, gbc);

        // Label d'erreur
        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel, gbc);

        // Bouton inscription
        JButton registerButton = createStyledButton("S'inscrire");
        registerButton.addActionListener(e -> {
            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!nom.matches("[a-zA-Z\\-]+")) {
                errorLabel.setText("Le nom ne doit contenir que des lettres ou des tirets.");
            } else if (!prenom.matches("[a-zA-Z\\-]+")) {
                errorLabel.setText("Le prénom ne doit contenir que des lettres ou des tirets.");
            } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                errorLabel.setText("Adresse email invalide.");
            } else if (password.length() < 8) {
                errorLabel.setText("Le mot de passe doit contenir au moins 8 caractères.");
            } else if (!password.equals(confirmPassword)) {
                errorLabel.setText("Les mots de passe ne correspondent pas.");
            } else {
                errorLabel.setText("Inscription réussie !");
                errorLabel.setForeground(new Color(0, 128, 0)); // vert
                // -> ici tu peux appeler ton modèle pour stocker l'utilisateur
            }
        });
        panel.add(registerButton, gbc);

        // Bouton retour accueil
        JButton returnButton = createStyledButton("Retour à l'accueil");
        returnButton.addActionListener(e -> showPage("Entry"));
        panel.add(returnButton, gbc);

        return panel;
    }



    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(245, 245, 220));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return button;
    }

    public void setVisible(boolean visible) {
        gifDialog.setVisible(visible);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void showPage(String pageName) {
        cardLayout.show(mainPanel, pageName);
    }

    public class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = ImageIO.read(getClass().getResource("/image/fond_image_home.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }
}
