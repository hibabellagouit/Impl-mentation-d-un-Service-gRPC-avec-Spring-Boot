package io.grpc.repositories;

import io.grpc.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repository pour l'accès aux données des comptes.
 * Fournit des opérations CRUD standard via l'extension JpaRepository.
 * 
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
@Repository
public interface CompteRepository extends JpaRepository<Compte, String> {
    // Les méthodes CRUD de base sont automatiquement fournies par JpaRepository
    // Des méthodes personnalisées peuvent être ajoutées ici si nécessaire
}