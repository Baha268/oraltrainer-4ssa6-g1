package com.oraltrainer.notification.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotification;

    private Long idUser;
    private String titre;
    private String message;

    @Enumerated(EnumType.STRING)
    private TypeNotification type;

    private Boolean lu;
    private LocalDateTime dateCreation;
}
