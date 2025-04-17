package modele;

public class Utilisateur {
    private int id;
    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;
    private String adresse;
    private int telephone;
    private boolean isAdmin;

    public Utilisateur() {
        this.id = -1;
        this.email = "INCONNU";
        this.motDePasse = "INCONNU";
        this.nom = "INCONNU";
        this.prenom = "INCONNU";
        this.adresse = "INCONNU";
        this.telephone = -1;
        this.isAdmin = false;
    }

    public Utilisateur(String email, String motDePasse, String nom, String prenom, String adresse, int telephone, Boolean isAdmin) {
        this.id = -1;
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.isAdmin = isAdmin;
    }

    public Utilisateur(int id, String email, String motDePasse, String nom, String prenom, String adresse, int telephone, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.isAdmin = isAdmin;
    }

    public int getId() { return id; }

    public String getEmail() { return email; }

    public String getMotDePasse() { return motDePasse; }

    public String getNom() { return nom; }

    public String getPrenom() { return prenom; }

    public String getAdresse() { return adresse; }

    public int getTelephone() { return telephone; }

    public boolean getIsAdmin() { return isAdmin; }

    public void setId(int id) { this.id = id; }

    public void setEmail(String email) { this.email = email; }

    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public void setNom(String nom) { this.nom = nom; }

    public void setPrenom(String prenom) { this.prenom = prenom; }

    public void setAdresse(String adresse) { this.adresse = adresse; }

    public void setTelephone(int telephone) { this.telephone = telephone; }

    public void setIsAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }
}
