package com.oraltrainer.notification.ServiceInterface;

import com.oraltrainer.notification.Entity.Notification;

import java.util.List;

public interface INotificationService {
    Notification creerNotification(Notification notification);
    Notification updateNotification(Notification notification);
    Notification supprimerNotification(Long id);
    Notification obtenirNotification(Long id);
    List<Notification> listerNotifications();
    List<Notification> listerNotificationsUser(Long idUser);
    Notification marquerCommeLue(Long id);
}
