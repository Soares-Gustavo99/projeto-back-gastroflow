package com.fiec.projeto_back_gastroflow.features.firebase.services;

import com.fiec.projeto_back_gastroflow.features.firebase.models.dto.NotificationMessage;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import com.fiec.projeto_back_gastroflow.features.user.repositories.UserRepository;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.FirebaseMessagingException;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {

    private final UserRepository userRepository;
    private final FirebaseMessaging firebaseMessaging;

    // Injetamos o Repositório e o FirebaseApp para obter a instância do FirebaseMessaging
    public NotificationService(UserRepository userRepository, FirebaseApp firebaseApp) {
        this.userRepository = userRepository;
        this.firebaseMessaging = FirebaseMessaging.getInstance(firebaseApp);
    }

    /**
     * Envia uma notificação para o token FCM de um usuário específico.
     * @param dto O DTO contendo o userId, title e message.
     * @return O ID da mensagem retornada pelo Firebase.
     * @throws RuntimeException Se o usuário ou o token FCM não for encontrado.
     * @throws FirebaseMessagingException Se houver erro de envio pelo Firebase.
     */
    public String sendNotificationToUser(NotificationMessage dto) throws FirebaseMessagingException {

        // 1. Busca o usuário e seu token FCM
        Optional<User> userOptional = userRepository.findById(UUID.fromString(dto.getAuthUserId()));

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuário com ID " + dto.getAuthUserId() + " não encontrado.");
        }

        String fcmToken = userOptional.get().getFcmToken();

        if (fcmToken == null || fcmToken.trim().isEmpty()) {
            throw new RuntimeException("Token FCM não encontrado para o usuário ID: " + dto.getAuthUserId());
        }

        // 2. Constrói o objeto Notification (para exibição na tela)
        Notification notification = Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getMessage())
                .build();

        // 3. Constrói o objeto Message do Firebase, usando o token específico
        Message message = Message.builder()
                .setToken(fcmToken) // Define o token do dispositivo de destino
                .setNotification(notification)
                // Opcional: Adiciona dados customizados para o cliente consumir
                .putData("custom_id", "12345")
                .build();

        // 4. Envia a mensagem e retorna o ID da resposta
        String response = firebaseMessaging.send(message);

        System.out.println("Notificação enviada com sucesso. ID da mensagem Firebase: " + response);

        return response;
    }
}