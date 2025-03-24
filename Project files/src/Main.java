// src/Main.java
import controleur.ShoppingController;
import modele.ShoppingModel;
import vue.ShoppingView;

public class Main {
    public static void main(String[] args) {
        ShoppingModel model = new ShoppingModel();
        ShoppingView view = new ShoppingView();
        ShoppingController controller = new ShoppingController(model, view);
        view.setVisible(true);
    }
}