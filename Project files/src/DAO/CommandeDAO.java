package DAO;

import modele.Commande;

import java.util.List;

public interface CommandeDAO {
    List<Commande> getAll();

    void ajouter(Commande commande);

    Commande chercher(int id);

    Commande modifier(Commande commande);

    void supprimer(int id);



}