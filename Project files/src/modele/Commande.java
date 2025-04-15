package modele;

public class Commande {
    private final int id;
    private final int clientID;
    private String date;
    private String statut;
    private double prix;

    public Commande(int id, int clientID, String date, String statut, double prix) {
        this.id = id;
        this.clientID = clientID;
        this.date = date;
        this.statut = statut;
        this.prix = prix;
    }

    public int getId() { return id; }

    public int getClientID() { return clientID; }

    public String getDate() { return date; }

    public String getStatut() { return statut; }

    public double getPrix() { return prix; }

    public void setDate(String date) { this.date = date; }

    public void setStatut(String statut) { this.statut = statut; }

    public void setPrix(double prix) { this.prix = prix; }
}

