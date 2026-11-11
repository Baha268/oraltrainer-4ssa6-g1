package com.oraltrainer.notification.Controller;

import com.oraltrainer.notification.Entity.Notification;
import com.oraltrainer.notification.ServiceInterface.INotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    INotificationService notificationService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Notification Microservice!";
    }

    // 201 Created
    @PostMapping("/AddNotification")
    public ResponseEntity<Notification> creerNotification(@RequestBody Notification notification) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.creerNotification(notification));
    }

    // 200 OK ou 404 Not Found
    @PutMapping("/update")
    public ResponseEntity<Notification> updateNotification(@RequestBody Notification notification) {
        if (notification.getIdNotification() == null
                || notificationService.obtenirNotification(notification.getIdNotification()) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notificationService.updateNotification(notification));
    }

    // 200 OK ou 404 Not Found
    @DeleteMapping("/DeleteNotification/{id}")
    public ResponseEntity<Notification> supprimerNotification(@PathVariable Long id) {
        Notification notification = notificationService.supprimerNotification(id);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notification);
    }

    // 200 OK ou 404 Not Found
    @GetMapping("/getNotification/{id}")
    public ResponseEntity<Notification> obtenirNotification(@PathVariable Long id) {
        Notification notification = notificationService.obtenirNotification(id);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/all")
    public List<Notification> listerNotifications() {
        return notificationService.listerNotifications();
    }

    @GetMapping("/user/{idUser}")
    public List<Notification> listerNotificationsUser(@PathVariable Long idUser) {
        return notificationService.listerNotificationsUser(idUser);
    }

    // 200 OK ou 404 Not Found
    @PutMapping("/lire/{id}")
    public ResponseEntity<Notification> marquerCommeLue(@PathVariable Long id) {
        Notification notification = notificationService.marquerCommeLue(id);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notification);
    }
}
