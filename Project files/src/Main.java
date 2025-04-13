
import controleur.ShoppingController;
import vue.ShoppingView;

public class Main {
    public static void main(String[] args) {
        ShoppingView view = new ShoppingView();
        ShoppingController controller = new ShoppingController(view);
        view.setVisible(true);
    }
}
