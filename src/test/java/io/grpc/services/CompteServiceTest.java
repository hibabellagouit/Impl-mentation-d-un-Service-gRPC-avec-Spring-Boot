package io.grpc.services;

import io.grpc.entities.Compte;
import io.grpc.repositories.CompteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompteServiceTest {

    @Mock
    private CompteRepository compteRepository;

    @InjectMocks
    private CompteService compteService;

    private Compte compte1;
    private Compte compte2;

    @BeforeEach
    void setUp() {
        // Initialisation des données de test
        compte1 = new Compte("1", 1000.0f, "2024-01-01", "COURANT");
        compte2 = new Compte("2", 2000.0f, "2024-01-02", "EPARGNE");
    }

    @Test
    void testFindAllComptes() {
        // Arrange
        List<Compte> comptes = Arrays.asList(compte1, compte2);
        when(compteRepository.findAll()).thenReturn(comptes);

        // Act
        List<Compte> result = compteService.findAllComptes();

        // Assert
        assertEquals(2, result.size());
        verify(compteRepository, times(1)).findAll();
    }

    @Test
    void testFindCompteById_WhenCompteExists() {
        // Arrange
        when(compteRepository.findById("1")).thenReturn(Optional.of(compte1));

        // Act
        Compte result = compteService.findCompteById("1");

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals(1000.0f, result.getSolde(), 0.001);
        verify(compteRepository, times(1)).findById("1");
    }

    @Test
    void testFindCompteById_WhenCompteNotExists() {
        // Arrange
        when(compteRepository.findById("999")).thenReturn(Optional.empty());

        // Act
        Compte result = compteService.findCompteById("999");

        // Assert
        assertNull(result);
        verify(compteRepository, times(1)).findById("999");
    }

    @Test
    void testSaveCompte() {
        // Arrange
        when(compteRepository.save(any(Compte.class))).thenReturn(compte1);

        // Act
        Compte savedCompte = compteService.saveCompte(compte1);

        // Assert
        assertNotNull(savedCompte);
        assertEquals(compte1.getId(), savedCompte.getId());
        verify(compteRepository, times(1)).save(compte1);
    }

    @Test
    void testDeleteCompte() {
        // Arrange
        doNothing().when(compteRepository).deleteById("1");

        // Act
        compteService.deleteCompte("1");

        // Assert
        verify(compteRepository, times(1)).deleteById("1");
    }

    @Test
    void testCalculateTotalSolde() {
        // Arrange
        List<Compte> comptes = Arrays.asList(compte1, compte2);
        when(compteRepository.findAll()).thenReturn(comptes);

        // Act
        float total = compteService.calculateTotalSolde();

        // Assert
        assertEquals(3000.0f, total, 0.001);
        verify(compteRepository, times(1)).findAll();
    }

    @Test
    void testCalculateAverageSolde() {
        // Arrange
        List<Compte> comptes = Arrays.asList(compte1, compte2);
        when(compteRepository.findAll()).thenReturn(comptes);

        // Act
        float average = compteService.calculateAverageSolde();

        // Assert
        assertEquals(1500.0f, average, 0.001);
        verify(compteRepository, times(1)).findAll();
    }

    @Test
    void testCalculateAverageSolde_WhenNoComptes() {
        // Arrange
        when(compteRepository.findAll()).thenReturn(List.of());

        // Act
        float average = compteService.calculateAverageSolde();

        // Assert
        assertEquals(0.0f, average, 0.001);
        verify(compteRepository, times(1)).findAll();
    }
}
