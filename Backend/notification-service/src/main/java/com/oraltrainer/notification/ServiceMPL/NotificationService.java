package com.oraltrainer.notification.ServiceMPL;

import com.oraltrainer.notification.Entity.Notification;
import com.oraltrainer.notification.Repository.NotificationRepository;
import com.oraltrainer.notification.ServiceInterface.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService {
    NotificationRepository notificationRepository;

    @Override
    public Notification creerNotification(Notification notification) {
        notification.setIdNotification(null); // l'id est genere par la base
        notification.setDateCreation(LocalDateTime.now());
        notification.setLu(false);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification supprimerNotification(Long id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            notificationRepository.delete(notification);
        }
        return notification;
    }

    @Override
    public Notification obtenirNotification(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Notification> listerNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> listerNotificationsUser(Long idUser) {
        return notificationRepository.findByIdUser(idUser);
    }

    @Override
    public Notification marquerCommeLue(Long id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            notification.setLu(true);
            notificationRepository.save(notification);
        }
        return notification;
    }
}
