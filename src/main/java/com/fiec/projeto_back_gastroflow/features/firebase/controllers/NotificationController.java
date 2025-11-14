package com.fiec.projeto_back_gastroflow.features.firebase.controllers;

import com.fiec.projeto_back_gastroflow.features.firebase.models.dto.FcmTokenRequest;
import com.fiec.projeto_back_gastroflow.features.firebase.models.dto.NotificationMessage;
import com.fiec.projeto_back_gastroflow.features.firebase.services.NotificationService;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import com.fiec.projeto_back_gastroflow.features.user.services.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Registra/Atualiza o token FCM do usuário logado", description = "Usado pelo aplicativo cliente para enviar o token de notificação Firebase (FCM) para o servidor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (e.g., token ausente)."),
            @ApiResponse(responseCode = "401", description = "Não autorizado.")
    })
    @PutMapping("/token")
    public void registerFcmToken(
            // Assume que o ID do usuário pode ser obtido via Principal
            // Se estiver usando Spring Security, você pode usar @AuthenticationPrincipal
            Authentication authentication,
            @Valid @RequestBody FcmTokenRequest request) {

        /// ... (omitting body for brevity, keeping only the logic that uses 'authentication')
        User myUser = (User) authentication.getPrincipal();

        System.out.println("Recebendo novo token FCM para o usuário ID: " + myUser.getId());

        // 2. Chama o serviço para atualizar o token
        userService.updateFcmToken(myUser.getId(), request);

    }

    @Operation(summary = "Envia uma notificação de teste a um usuário", description = "Endpoint para envio manual de notificação para fins de teste.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificação enviada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados da mensagem inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao enviar a notificação via Firebase.")
    })
    @PostMapping("/sendToUser")
    public String sendToUser(@RequestBody NotificationMessage dto) throws FirebaseMessagingException {
        return notificationService.sendNotificationToUser(dto);
    }
}