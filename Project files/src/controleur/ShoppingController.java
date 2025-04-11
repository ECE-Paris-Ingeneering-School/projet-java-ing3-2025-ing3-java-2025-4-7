package controleur;

import modele.ShoppingModel;
import vue.ShoppingView;

public class ShoppingController {
    private ShoppingModel model;
    private ShoppingView view;

    public ShoppingController(ShoppingModel model, ShoppingView view) {
        this.model = model;
        this.view = view;

    }
}
