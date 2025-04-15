package modele;

public class Article {
    private final int id;
    private String nom;
    private String marque;
    private double prixUnitaire;
    private double prixVrac;
    private int seuilVrac;
    private int stock;

    public Article(int id, String nom, String marque, double prixUnitaire, double prixVrac, int seuilVrac, int stock) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.prixVrac = prixVrac;
        this.seuilVrac = seuilVrac;
        this.stock = stock;
    }

    public int getId() { return id; }

    public String getNom() { return nom; }

    public String getMarque() { return marque; }

    public double getPrixUnitaire() { return prixUnitaire; }

    public double getPrixVrac() { return prixVrac; }

    public int getSeuilVrac() { return seuilVrac; }

    public int getStock() { return stock; }

    public void setNom(String nom) { this.nom = nom; }

    public void setMarque(String marque) { this.marque = marque; }

    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public void setPrixVrac(int prixVrac) { this.prixVrac = prixVrac; }

    public void setSeuilVrac(int seuilVrac) { this.seuilVrac = seuilVrac; }

    public void setStock(int stock) { this.stock = stock; }
}
