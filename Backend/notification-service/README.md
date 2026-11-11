# notification-service

Microservice de **notifications** de la plateforme **OralTrainer** (module AWD, 2026-2027).
Responsable : Med Hedi Guidara · Base : MySQL · Port : `8088`

## 1. Responsabilité (contexte borné)

Le notification-service informe les utilisateurs de ce qui se passe sur la plateforme :

- informer l'apprenant qu'une **analyse orale est terminée** ;
- prévenir l'apprenant qu'un **formateur a ajouté un commentaire** ;
- informer l'apprenant de sa **progression** ;
- envoyer les notifications liées aux **simulations d'examen**.

Ce qu'il **ne fait pas** : il ne calcule ni les scores, ni le feedback, ni la progression. Il **réagit** seulement aux actions des autres services et stocke les notifications.

## 2. Modèle de données

### Entité `Notification`

| Champ | Type | Description |
|---|---|---|
| `idNotification` | Long | Clé primaire, générée par la base |
| `idUser` | Long | Destinataire (id venant du user-service) |
| `titre` | String | Titre court |
| `message` | String | Contenu de la notification |
| `type` | Enum `TypeNotification` | `ANALYSE_TERMINEE`, `COMMENTAIRE_FORMATEUR`, `PROGRESSION`, `SIMULATION_EXAMEN` |
| `lu` | Boolean | `false` à la création, `true` quand l'apprenant l'a lue |
| `dateCreation` | LocalDateTime | Remplie automatiquement à la création |

Pas de relation JPA avec les autres services : on garde seulement `idUser`.

### Choix de la base : MySQL

- **Une base dédiée** (`notification_db`) : aucun autre service n'y accède (principe *une base par microservice*).
- Les données sont **simples et structurées** (une seule table, colonnes fixes) : une base relationnelle suffit.
- Requêtes fréquentes du type « notifications d'un utilisateur » : faciles et rapides avec un index sur `id_user`.
- MySQL est maîtrisé par l'équipe et fait partie de la stack imposée.

## 3. API REST

Préfixe : `/notification` · Format : JSON · Documentation : `http://localhost:8088/swagger-ui/index.html`

| Méthode | URI | Rôle | Succès | Erreur |
|---|---|---|---|---|
| GET | `/notification/hello` | Vérifier que le service répond | 200 | — |
| POST | `/notification/AddNotification` | Créer une notification | 201 | 400 (JSON invalide) |
| GET | `/notification/all` | Lister toutes les notifications | 200 | — |
| GET | `/notification/getNotification/{id}` | Obtenir une notification | 200 | 404 |
| GET | `/notification/user/{idUser}` | Notifications d'un utilisateur | 200 | — |
| PUT | `/notification/update` | Modifier une notification | 200 | 404 |
| PUT | `/notification/lire/{id}` | Marquer comme lue | 200 | 404 |
| DELETE | `/notification/DeleteNotification/{id}` | Supprimer une notification | 200 | 404 |

Exemple de création :

```json
POST /notification/AddNotification
{
  "idUser": 1,
  "titre": "Analyse terminée",
  "message": "Votre enregistrement a été analysé",
  "type": "ANALYSE_TERMINEE"
}
```

## 4. Interactions avec les autres microservices

Le notification-service est surtout un service **consommateur** : les autres services le préviennent quand il faut notifier quelqu'un.

| Service appelant | Événement / appel | Notification créée | Mode |
|---|---|---|---|
| analysis-service / recording-service | Analyse ou transcription terminée | `ANALYSE_TERMINEE` | Asynchrone (RabbitMQ) |
| feedback-service | Commentaire ajouté ou validé par le formateur | `COMMENTAIRE_FORMATEUR` | Asynchrone (RabbitMQ) |
| progress-service | Progression mise à jour | `PROGRESSION` | Asynchrone (RabbitMQ) |
| simulation-service | Épreuve blanche terminée | `SIMULATION_EXAMEN` | Asynchrone (RabbitMQ) |
| Front-end (via API Gateway) | Lire ses notifications, marquer comme lue | — | Synchrone (HTTP REST) |
| user-service | Vérifier / récupérer l'utilisateur (optionnel) | — | Synchrone (OpenFeign) |

**Pourquoi l'asynchrone ?** Le service qui termine une analyse n'a pas besoin d'attendre la notification. Avec RabbitMQ, il publie un événement et continue son travail : les deux services restent **découplés**.

### Si un service est indisponible

- **notification-service en panne** : les messages restent dans la file RabbitMQ et sont traités au redémarrage, aucune notification n'est perdue. Les autres services continuent de fonctionner.
- **user-service en panne** : la notification est quand même enregistrée avec `idUser` ; la vérification est refaite plus tard.
- **Message reçu deux fois** : prévoir un identifiant d'événement pour ignorer les doublons (étape suivante).

## 5. Architecture du code

```
com.oraltrainer.notification
├── NotificationServiceApplication.java
├── Entity/            Notification, TypeNotification
├── Repository/        NotificationRepository (JpaRepository)
├── ServiceInterface/  INotificationService
├── ServiceMPL/        NotificationService
└── Controller/        NotificationController
```

## 6. Lancement

Prérequis : Java 17+, MySQL démarré (XAMPP, port 3306, utilisateur `root` sans mot de passe).
La base `notification_db` est créée automatiquement.

```bash
cd Backend/notification-service
./mvnw spring-boot:run
```

Puis ouvrir `http://localhost:8088/swagger-ui/index.html`.

## 7. Étapes suivantes

- Enregistrement dans **Eureka** et passage par l'**API Gateway**.
- Consommation des événements **RabbitMQ** (`oral.events`).
- Sécurisation avec **Keycloak** (un apprenant ne voit que ses notifications).
