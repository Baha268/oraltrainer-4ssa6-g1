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
| GET | `/api/feedbacks?userId=&page=&size=` | Historique paginé |
