### 4.5 `feedback-service` — Étudiant 5 : Feriel Dridi

| | |
|---|---|
| **Port** | 8085 |
| **Base de données** | H2 (en mémoire, `jdbc:h2:mem:feedbackdb`) |
| **Swagger UI** | http://localhost:8085/swagger-ui/index.html |
| **Console H2** | http://localhost:8085/h2-console (utilisateur `sa`, sans mot de passe) |

#### Responsabilités
- Générer le feedback textuel d'un enregistrement : corrections de **grammaire**, de **lexique** et de **cohérence**, reformulations et commentaire global.
- Calculer les scores lexique / grammaire / cohérence et les regrouper avec les trois scores acoustiques (prononciation, fluidité, intonation) reçus d'`analysis-service`.
- Recommander des exercices ciblant les erreurs récurrentes.
- Permettre au formateur de **valider** ou d'**ajuster** l'évaluation et d'ajouter un commentaire.

#### Modèle de données
| Entité | Rôle |
|---|---|
| `Feedback` | Feedback d'un enregistrement : `recordingId`, `userId`, langue, 6 scores, commentaire global, statut, date |
| `Correction` | Erreur relevée : catégorie (`GRAMMAR`, `LEXICON`, `COHERENCE`), texte original, suggestion, explication, position |
| `RecommendedExercise` | Exercice recommandé (`exerciseId` de content-service) et raison |
| `TrainerReview` | Évaluation du formateur (0..1 par feedback) : scores ajustés, commentaire, validation |

Statut d'un feedback : `GENERATED` → `VALIDATED` (validé sans modification) ou `ADJUSTED` (au moins un score ajusté).
Aucune jointure avec les autres services : seuls les identifiants (`recordingId`, `userId`, `exerciseId`, `trainerId`) sont stockés.

#### Contrat d'API
| Méthode | Endpoint | Description | Codes |
|---|---|---|---|
| GET | `/api/feedbacks/recording/{recordingId}` | Feedback d'un enregistrement | 200, 404 |
| GET | `/api/feedbacks/{id}` | Détail d'un feedback | 200, 404 |
| GET | `/api/feedbacks?userId=&page=&size=` | Historique paginé | 200, 400 |
| GET | `/api/feedbacks/{id}/corrections` | Corrections détaillées | 200, 404 |
| GET | `/api/recommendations/{userId}` | Exercices recommandés | 200, 404 |
| PUT | `/api/feedbacks/{id}/review` | Valider ou ajuster les scores (formateur) | 200, 400, 404, 409 |
| POST | `/api/feedbacks/{id}/comments` | Ajouter un commentaire de formateur | 201, 400, 404 |
| GET | `/api/feedbacks/hello` | Test de disponibilité du service | 200 |

Erreurs au format RFC 7807 (`ProblemDetail`).

#### Communications
- **Asynchrone (RabbitMQ)** : consomme `AnalysisCompleted` (`analysis.completed`) ; publie `FeedbackGenerated` (`feedback.generated`) et `FeedbackValidated` (`feedback.validated`), consommés par progress-service et simulation-service.
- **Synchrone (OpenFeign)** : `recording-service` → `GET /api/recordings/{id}/transcript` (texte à corriger) ; `content-service` → `GET /api/exercises`, `GET /api/grids` (exercices et critères).

#### Lancer le service
1. Ouvrir `Backend/feedback-service` dans IntelliJ et recharger Maven.
2. Lancer `FeedbackServiceApplication` (Java 17, Spring Boot 3.2.2).
3. Ouvrir Swagger : des données de démonstration (3 feedbacks) sont chargées au démarrage.

#### Avancement
- [x] Entités, repositories, services, contrôleurs REST
- [x] Documentation Swagger / OpenAPI
- [x] Gestion des erreurs (400, 404, 409)
- [ ] Événements RabbitMQ (`AnalysisCompleted`, `FeedbackGenerated`, `FeedbackValidated`)
- [ ] Appels OpenFeign vers recording-service et content-service
- [ ] Sécurité Keycloak (`@PreAuthorize`)
