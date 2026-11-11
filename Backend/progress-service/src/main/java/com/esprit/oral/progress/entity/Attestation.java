package com.esprit.oral.progress.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attestation")
public class Attestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String code;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long certificationId;

    @Column(nullable = false, length = 5)
    private String level;

    @Column(nullable = false)
    private LocalDate issuedAt;

    /** Trainer or institution user id who issued it. */
    @Column(nullable = false)
    private Long issuedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AttestationStatus status = AttestationStatus.ISSUED;

    @PrePersist
    void onCreate() {
        if (issuedAt == null) issuedAt = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getCertificationId() { return certificationId; }
    public void setCertificationId(Long certificationId) { this.certificationId = certificationId; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public LocalDate getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDate issuedAt) { this.issuedAt = issuedAt; }
    public Long getIssuedBy() { return issuedBy; }
    public void setIssuedBy(Long issuedBy) { this.issuedBy = issuedBy; }
    public AttestationStatus getStatus() { return status; }
    public void setStatus(AttestationStatus status) { this.status = status; }
}
