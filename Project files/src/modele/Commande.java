package modele;

public class Commande {
    private final int id;
    private final int utilisateurID;
    private final String date;
    private String statut;
    private final double prix;
    private final String listeID_Article;
    private final String listeQuantite_Article;
    private final String adresseLivraison; // 👈 Nouveau champ

    public Commande(int id, int utilisateurID, String date, String statut, double prix,
                    String listeID_Article, String listeQuantite_Article, String adresseLivraison) {
        this.id = id;
        this.utilisateurID = utilisateurID;
        this.date = date;
        this.statut = statut;
        this.prix = prix;
        this.listeID_Article = listeID_Article;
        this.listeQuantite_Article = listeQuantite_Article;
        this.adresseLivraison = adresseLivraison; // 👈 Ajout au constructeur
    }

    public int getId() { return id; }

    public int getUtilisateurID() { return utilisateurID; }

    public String getDate() { return date; }

    public String getStatut() { return statut; }

    public double getPrix() { return prix; }

    public String getListeID_Article() { return listeID_Article; }

    public String getListeQuantite_Article() { return listeQuantite_Article; }

    public String getAdresseLivraison() { return adresseLivraison; } // 👈 Getter correct

    public void setStatut(String statut) { this.statut = statut; }
}
