package modele;

public class Article {
    private int id;
    private String nom;
    private String marque;
    private double prixUnitaire;
    private double prixVrac;
    private int seuilVrac;
    private int stock;
    private boolean isAvailable;
    private String imageURL;  // Nouveau champ pour l'URL de l'image

    // Constructeur mis à jour
    public Article(int id, String nom, String marque, double prixUnitaire, double prixVrac,
                   int seuilVrac, int stock, boolean isAvailable, String imageURL) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.prixVrac = prixVrac;
        this.seuilVrac = seuilVrac;
        this.stock = stock;
        this.isAvailable = isAvailable;
        this.imageURL = imageURL;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getMarque() {
        return marque;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public double getPrixVrac() {
        return prixVrac;
    }

    public int getSeuilVrac() {
        return seuilVrac;
    }

    public int getStock() {
        return stock;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setId(int id) { this.id = id; };

    public String getImageUrl() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
