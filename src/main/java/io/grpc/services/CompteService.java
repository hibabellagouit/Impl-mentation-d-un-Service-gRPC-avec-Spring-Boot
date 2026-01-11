package io.grpc.services;

import io.grpc.entities.Compte;
import io.grpc.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour la gestion des opérations métier liées aux comptes.
 * Fournit des méthodes pour la gestion CRUD des comptes ainsi que des opérations métier spécifiques.
 */
@Service
public class CompteService {

    private final CompteRepository compteRepository;

    /**
     * Constructeur avec injection de dépendance
     * @param compteRepository Le repository pour l'accès aux données des comptes
     */
    @Autowired
    public CompteService(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }

    /**
     * Récupère tous les comptes existants
     * @return Une liste de tous les comptes
     */
    public List<Compte> findAllComptes() {
        return compteRepository.findAll();
    }

    /**
     * Recherche un compte par son identifiant
     * @param id L'identifiant du compte à rechercher
     * @return Le compte trouvé ou null si aucun compte n'est trouvé
     */
    public Compte findCompteById(String id) {
        return compteRepository.findById(id).orElse(null);
    }

    /**
     * Enregistre un nouveau compte ou met à jour un compte existant
     * @param compte Le compte à enregistrer ou mettre à jour
     * @return Le compte enregistré avec son identifiant généré
     */
    public Compte saveCompte(Compte compte) {
        return compteRepository.save(compte);
    }

    /**
     * Supprime un compte par son identifiant
     * @param id L'identifiant du compte à supprimer
     */
    public void deleteCompte(String id) {
        compteRepository.deleteById(id);
    }

    /**
     * Calcule la somme totale des soldes de tous les comptes
     * @return Le montant total de tous les soldes
     */
    public float calculateTotalSolde() {
        return (float) compteRepository.findAll()
                .stream()
                .mapToDouble(Compte::getSolde)
                .sum();
    }

    /**
     * Calcule la moyenne des soldes de tous les comptes
     * @return La moyenne des soldes, ou 0 si aucun compte n'existe
     */
    public float calculateAverageSolde() {
        List<Compte> comptes = compteRepository.findAll();
        if (comptes.isEmpty()) {
            return 0;
        }
        return (float) comptes.stream()
                .mapToDouble(Compte::getSolde)
                .average()
                .orElse(0);
    }
}