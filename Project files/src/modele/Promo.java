package modele;

/**
 * Classe d'un code promotionnel pouvant être appliqué à une commande.
 *
 * Contient des informations sur l'identifiant du code, le code lui-même,
 * le pourcentage de réduction, et son état d'activation.
 */
public class Promo {
    private int promoID;
    private String code;
    private float reduction;
    private boolean active;

    /**
     * Construit un objet promotion avec tous ses attributs spécifiés.
     *
     * @param promoID    Identifiant de la promotion
     * @param code       Code promotionnel
     * @param reduction  Pourcentage de réduction (ex: 0.2 pour 20%)
     * @param active     État d'activation du code
     */
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
