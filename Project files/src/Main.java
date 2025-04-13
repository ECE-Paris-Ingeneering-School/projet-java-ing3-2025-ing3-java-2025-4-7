import vue.ShoppingView;
import controleur.ShoppingController;

public class Main {
    public static void main(String[] args) {
        ShoppingView view = new ShoppingView();
        new ShoppingController(view);
        view.setVisible(true);
        view.showPage("HomePage");
    }
}