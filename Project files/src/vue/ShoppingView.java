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
    private JPanel homePanel;
    private JPanel loginPanel;
    private CardLayout cardLayout;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toggleButton;
    private JLabel gifLabel;
    private Image icon; // Make icon a class member
    private int currentFrame = 1;
    private static final int TOTAL_FRAMES = 49;
    private Timer timer;

    public ShoppingView() {
        // Create a dialog for the image sequence animation
        gifDialog = new JDialog();
        gifDialog.setUndecorated(true);
        gifDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        gifDialog.setResizable(false);

        // Set the window icon
        try {
            icon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/image/logo.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        homePanel = createHomePanel();
        loginPanel = createLoginPanel();

        mainPanel.add(homePanel, "Home");
        mainPanel.add(loginPanel, "Login");

        // Add label for image sequence animation
        gifLabel = new JLabel();
        gifDialog.add(gifLabel, BorderLayout.CENTER);

        // Resize dialog to match the first image size
        updateGifLabel();
        gifDialog.pack();
        gifDialog.setLocationRelativeTo(null); // Center the dialog

        // Set a timer to update the image and show the main panel after the last frame
        timer = new Timer(100, e -> {
            if (currentFrame <= TOTAL_FRAMES) {
                updateGifLabel();
                currentFrame++;
            } else {
                timer.stop(); // Stop the timer
                gifDialog.dispose(); // Dispose the dialog
                createMainFrame(); // Create and show the main frame
            }
        });
        timer.start();
    }

    private void updateGifLabel() {
        String framePath = String.format("/gif/blog/frame-%03d.gif", currentFrame);
        gifLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(framePath))));
    }

    private void createMainFrame() {
        frame = new JFrame("Shopping App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(800, 600); // Set the desired size for the decorated frame
        frame.setIconImage(icon); // Set the window icon again
        frame.add(mainPanel); // Add the main panel
        frame.setVisible(true); // Make the frame visible
    }

    private JPanel createHomePanel() {
        JPanel panel = new BackgroundPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel welcomeLabel = new JLabel("Bienvenue dans l'application de shopping!");
        welcomeLabel.setForeground(Color.BLACK);
        panel.add(welcomeLabel, gbc);

        loginButton = createStyledButton("Connexion");
        panel.add(loginButton, gbc);

        registerButton = createStyledButton("Inscription");
        panel.add(registerButton, gbc);

        toggleButton = createStyledButton("Enlever l'écran plein");
        panel.add(toggleButton, gbc);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Add resized logo at the top right
//        JLabel logoLabel = new JLabel();
//        try {
//            Image logoImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/image/logo.jpg")));
//            Image scaledLogoImage = logoImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Resize the logo
//            logoLabel.setIcon(new ImageIcon(scaledLogoImage));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        gbc.anchor = GridBagConstraints.NORTHEAST;
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        panel.add(logoLabel, gbc);

        // Add email field
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Add password field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Mot de passe:");
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Add login button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton loginButton = createStyledButton("Connexion");
        panel.add(loginButton, gbc);

        //Add return home button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton returnHomeButton = createStyledButton("Retour à l'accueil");
        returnHomeButton.addActionListener(e -> showPage("Home"));
        panel.add(returnHomeButton, gbc);

        //Add register button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton registerButton = createStyledButton("Inscription");
        panel.add(registerButton, gbc);

        return panel;
    }

    private JPanel createInscriptionPanel()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Add email field
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Add password field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Mot de passe:");
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Add login button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton loginButton = createStyledButton("Connexion");
        panel.add(loginButton, gbc);

        //Add return home button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton returnHomeButton = createStyledButton("Retour à l'accueil");
        returnHomeButton.addActionListener(e -> showPage("Home"));
        panel.add(returnHomeButton, gbc);

        //Add register button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton registerButton = createStyledButton("Inscription");
        panel.add(registerButton, gbc);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(245, 245, 220)); // Beige color
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

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getToggleButton() {
        return toggleButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
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