# Service gRPC avec Spring Boot

Ce projet est une implémentation d'un service gRPC avec Spring Boot pour gérer des comptes bancaires.

## Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur

## Structure du projet
src/ ├── main/ │ ├── java/io/grpc/ │ │ ├── entities/ # Entités JPA │ │ ├── repositories/ # Répertoires de données │ │ └── services/ # Logique métier │ └── resources/ # Fichiers de configuration └── test/ # Tests unitaires et d'intégration

 
## Installation
 
1. Cloner le dépôt
2. Compiler le projet :
   ```bash
   mvn clean install
Démarrer l'application
bash
mvn spring-boot:run
API
Comptes
GET /api/comptes - Récupérer tous les comptes
GET /api/comptes/{id} - Récupérer un compte par ID
POST /api/comptes - Créer un nouveau compte
DELETE /api/comptes/{id} - Supprimer un compte
Base de données
L'application utilise H2 en mémoire. La console H2 est disponible à l'adresse :

URL : http://localhost:8080/h2-console
JDBC URL : jdbc:h2:mem:testdb
Username : 
Password : 
Tests
Exécuter les tests :

bash
mvn test
Technologies utilisées
Spring Boot
Spring Data JPA
H2 Database
gRPC (en cours d'implémentation)
Maven
