package com.fiec.projeto_back_gastroflow.features.firebase.models.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationMessage {
    private String authUserId;
    private String title;
    private String message;
}