package modele;

/**
 * Classe de la commande passée par un utilisateur.
 *
 * Contient les informations principales comme la date, le statut,
 * les articles commandés, les quantités, le prix total et l'adresse de livraison.
 */
public class Commande {
    private int id;
    private final int utilisateurID;
    private String date;
    private String statut;
    private double prix;
    private String listeID_Article;
    private String listeQuantite_Article;
    private String adresseLivraison; // 👈 Nouveau champ

    /**
     * Constructeur d'un objet commande avec toutes ses informations.
     *
     * @param id                    Identifiant de la commande
     * @param utilisateurID         Identifiant de l'utilisateur ayant passé la commande
     * @param date                  Date de la commande
     * @param statut                Statut de la commande
     * @param prix                  Prix total de la commande
     * @param listeID_Article       Liste des IDs des articles commandés
     * @param listeQuantite_Article Liste des quantités correspondantes
     * @param adresseLivraison      Adresse de livraison
     */
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

    public void setAdresse(String adresseLivraison) { this.adresseLivraison = adresseLivraison; };

    public void setStatut(String statut) { this.statut = statut; }
}
