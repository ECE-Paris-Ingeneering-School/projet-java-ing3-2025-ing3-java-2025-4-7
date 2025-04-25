package DAO;

import modele.Promo;
import java.util.ArrayList;

public interface PromoDAO {
    ArrayList<Promo> getAll();
    Promo ajouter(Promo promo);
    Promo chercher(int id);
    Promo modifier(Promo promo);
    void supprimer(Promo promo);
    Promo chercherParCode(String code);
}
