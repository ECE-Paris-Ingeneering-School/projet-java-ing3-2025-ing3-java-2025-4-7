package modele;

public class Commande {
    private final int id;
    private final int utilisateurID;
    private final String date;
    private String statut;
    private final double prix;
    private final String listeID_Article;
    private final String listeQuantite_Article;

    public Commande(int id, int utilisateurID, String date, String statut, double prix, String listeID_Article, String listeQuantite_Article) {
        this.id = id;
        this.utilisateurID = utilisateurID;
        this.date = date;
        this.statut = statut;
        this.prix = prix;
        this.listeID_Article = listeID_Article;
        this.listeQuantite_Article = listeQuantite_Article;
    }

    public int getId() { return id; }

    public int getUtilisateurID() { return utilisateurID; }

    public String getDate() { return date; }

    public String getStatut() { return statut; }

    public double getPrix() { return prix; }

    public String getListeID_Article() { return listeID_Article; }

    public String getListeQuantite_Article() { return listeQuantite_Article; }

    public void setStatut(String statut) { this.statut = statut; }
}

