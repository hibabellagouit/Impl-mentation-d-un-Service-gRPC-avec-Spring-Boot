package io.grpc.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private float solde;
    private String dateCreation;
    private String type;

    /**
     * Crée une nouvelle instance de Compte avec des valeurs par défaut
     */
    public Compte() {
    }

    /**
     * Crée une nouvelle instance de Compte avec les paramètres spécifiés
     * @param id Identifiant unique du compte
     * @param solde Solde initial du compte
     * @param dateCreation Date de création du compte au format texte
     * @param type Type de compte (épargne, courant, etc.)
     */
    public Compte(String id, float solde, String dateCreation, String type) {
        this.id = id;
        this.solde = solde;
        this.dateCreation = dateCreation;
        this.type = type;
    }

    /**
     * @return L'identifiant unique du compte
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant du compte
     * @param id Le nouvel identifiant du compte
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Le solde actuel du compte
     */
    public float getSolde() {
        return solde;
    }

    /**
     * Définit le solde du compte
     * @param solde Le nouveau solde du compte
     */
    public void setSolde(float solde) {
        this.solde = solde;
    }

    /**
     * @return La date de création du compte au format texte
     */
    public String getDateCreation() {
        return dateCreation;
    }

    /**
     * Définit la date de création du compte
     * @param dateCreation La nouvelle date de création au format texte
     */
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * @return Le type de compte (épargne, courant, etc.)
     */
    public String getType() {
        return type;
    }

    /**
     * Définit le type de compte
     * @param type Le nouveau type de compte
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "id='" + id + '\'' +
                ", solde=" + solde +
                ", dateCreation='" + dateCreation + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}