package modele;

public class Commande {
    private int id;
    private final int utilisateurID;
    private String date;
    private String statut;
    private double prix;
    private String listeID_Article;
    private String listeQuantite_Article;
    private String adresseLivraison; // 👈 Nouveau champ

    public Commande(int id, int utilisateurID, String date, String statut, double prix,
                    String listeID_Article, String listeQuantite_Article, String adresseLivraison) {
        this.id = id;
        this.utilisateurID = utilisateurID;
        this.date = date;
        this.statut = statut;
        this.prix = prix;
        this.listeID_Article = listeID_Article;
        this.listeQuantite_Article = listeQuantite_Article;
        this.adresseLivraison = adresseLivraison;
    }

    public int getId() { return id; }

    public int getUtilisateurID() { return utilisateurID; }

    public String getDate() { return date; }

    public String getStatut() { return statut; }

    public double getPrix() { return prix; }

    public String getListeID_Article() { return listeID_Article; }

    public String getListeQuantite_Article() { return listeQuantite_Article; }

    public String getAdresseLivraison() { return adresseLivraison; }

    public void setId(int id) { this.id = id; }

    public void setDate(String date) { this.date = date; }

    public void setPrix(double prix) { this.prix = prix; }

    public void setListeID_Article(String listeID_Article) { this.listeID_Article = listeID_Article; }

    public void setListeQuantite_Article(String listeQuantite_Article) {this.listeQuantite_Article = listeQuantite_Article; }

    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; };

    public void setStatut(String statut) { this.statut = statut; }
}
