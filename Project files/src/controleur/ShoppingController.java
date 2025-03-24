package controleur;
import javax.swing.JFrame;
import vue.ShoppingView;
import modele.ShoppingModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingController {
    private ShoppingModel model;
    private ShoppingView view;

    public ShoppingController(ShoppingModel model, ShoppingView view) {
        this.model = model;
        this.view = view;

        view.getToggleButton().addActionListener(new ActionListener() {
            private boolean isFullscreen = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isFullscreen) {
                    view.getFrame().setExtendedState(JFrame.NORMAL);
                    view.getFrame().setSize(800, 600);
                    view.getToggleButton().setText("Mettre en plein écran");
                } else {
                    view.getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
                    view.getToggleButton().setText("Enlever l'écran plein");
                }
                isFullscreen = !isFullscreen;
            }
        });

        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                view.showPage("Login");
            }
        });

        view.getRegisterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.showPage("Register");
            }
        });
    }
}