package com.fiec.projeto_back_gastroflow.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    // 1. Caminho para o arquivo de chave JSON da conta de serviço
    // Coloque o arquivo JSON na pasta src/main/resources
    @Value("classpath:firebase-service-account.json")
    private Resource serviceAccountResource;

    // 2. URL opcional do seu Realtime Database ou Storage Bucket (opcional, mas recomendado)
    @Value("${firebase.database-url:https://estoque-tcc.firebaseio.com}") // Substitua pela sua URL real
    private String databaseUrl;

    /**
     * Retorna o objeto FirebaseApp configurado como um Bean do Spring.
     * @return A instância do FirebaseApp.
     * @throws IOException Se o arquivo de credenciais não for encontrado.
     */
    @Bean
    public FirebaseApp firebaseApp() throws IOException {

        if (FirebaseApp.getApps().isEmpty()) {

            // Tenta obter o InputStream do arquivo JSON
            InputStream serviceAccount = serviceAccountResource.getInputStream();

            // Constrói as opções de inicialização do Firebase
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    //.setDatabaseUrl(databaseUrl)
                    // Outras opções como setStorageBucket() podem ser adicionadas aqui
                    .build();

            // Inicializa o FirebaseApp
            FirebaseApp app = FirebaseApp.initializeApp(options);

            System.out.println("FirebaseApp inicializado com sucesso para o ID: " + app.getName());
            return app;
        }

        // Se já estiver inicializado (em casos de auto-reload, etc.), retorna a instância padrão
        return FirebaseApp.getInstance();
    }
}
