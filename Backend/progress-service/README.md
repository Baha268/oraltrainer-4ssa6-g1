# progress-service (port 8086, MySQL `progress_db`)

Run: `DB_PASSWORD=yourpwd mvn spring-boot:run`, then open http://localhost:8086/swagger-ui.html

On Windows PowerShell: `$env:DB_PASSWORD="yourpwd"; mvn spring-boot:run`

Env vars: DB_URL, DB_USER, DB_PASSWORD, EUREKA_ENABLED (default false), EUREKA_URL, RABBIT_HOST.

Entities follow Figure 8 of the design document (ProgressSnapshot, Alert, Attestation + enums).
Added beyond the diagram, to confirm with the team: snapshot eventId/feedbackId/recordingId/simulationId
(idempotence + FeedbackValidated), Alert.snapshot link, enum values for Severity and AlertStatus.
