package com.fiec.projeto_back_gastroflow.features.firebase.controllers;

import com.fiec.projeto_back_gastroflow.features.firebase.models.dto.FcmTokenRequest;
import com.fiec.projeto_back_gastroflow.features.firebase.models.dto.NotificationMessage;
import com.fiec.projeto_back_gastroflow.features.firebase.services.NotificationService;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import com.fiec.projeto_back_gastroflow.features.user.services.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/api/notifications")
@AllArgsConstructor
public class NotificationController {

    private final UserService userService;
    private final NotificationService notificationService;

    /**
     * Endpoint para receber e atualizar o token FCM para o usuário logado.
     * * @param principal Objeto injetado pelo Spring Security representando o usuário logado.
     * @param request O DTO contendo o novo fcmToken.
     * @return Resposta 200 OK com o usuário atualizado (ou um DTO de resposta).
     */
    @PutMapping("/token")
    public void registerFcmToken(
            // Assume que o ID do usuário pode ser obtido via Principal
            // Se estiver usando Spring Security, você pode usar @AuthenticationPrincipal
            Authentication authentication,
            @Valid @RequestBody FcmTokenRequest request) {

        // 1. Simulação de obtenção do ID do usuário logado (MUITO IMPORTANTE!)
        // Na vida real, 'principal.getName()' retornaria o username/email.
        // Você precisaria de um método para buscar o User ID a partir desse nome.

        // Exemplo: Simular que o ID é 1L para fins didáticos,
        // mas você deve implementar a lógica real de extração do ID do usuário logado.


        // **Lógica Real com Spring Security:**
        // UserDetails userDetails = (UserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        // Long userId = userRepository.findByEmail(userDetails.getUsername()).getId();

        User myUser = (User) authentication.getPrincipal();

        System.out.println("Recebendo novo token FCM para o usuário ID: " + myUser.getId());

        // 2. Chama o serviço para atualizar o token
        userService.updateFcmToken(myUser.getId(), request);

    }

    @PostMapping("/sendToUser")
    public String sendToUser(@RequestBody NotificationMessage dto) throws FirebaseMessagingException {
        return notificationService.sendNotificationToUser(dto);
    }
}