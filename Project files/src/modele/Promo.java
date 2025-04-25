package modele;

public class Promo {
    private int promoID;
    private String code;
    private float reduction;
    private boolean active;

    public Promo(int promoID, String code, float reduction, boolean active) {
        this.promoID = promoID;
        this.code = code;
        this.reduction = reduction;
        this.active = active;
    }

    public Promo() {
        this.promoID = 0;
        this.code = "";
        this.reduction = 0.0f;
        this.active = false;
    }

    public int getPromoID() {
        return promoID;
    }
    public void setPromoID(int promoID) {
        this.promoID = promoID;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public float getReduction() {
        return reduction;
    }
    public void setReduction(float reduction) {
        this.reduction = reduction;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
