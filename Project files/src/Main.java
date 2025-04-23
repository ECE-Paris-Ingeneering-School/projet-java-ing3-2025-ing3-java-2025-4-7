import vue.*;
import controleur.*;

public class Main {
    public static void main(String[] args) {
        ShoppingView view = new ShoppingView();
        new ShoppingController(view);
        view.setVisible(true);
        view.showPage("HomePage");
        //bien fou matheo
    }
}