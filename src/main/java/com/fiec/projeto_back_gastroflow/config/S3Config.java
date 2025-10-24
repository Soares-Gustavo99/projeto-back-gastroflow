package com.fiec.projeto_back_gastroflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    // Carrega valores do application.properties
    @Value("${cloud.aws.credentials.access-key:}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key:}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        // Se as chaves de acesso estiverem configuradas, use-as
        if (!accessKey.isEmpty() && !secretKey.isEmpty()) {
            return S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey, secretKey)))
                    .build();
        }

        // Caso contrário, usa o provedor de credenciais padrão do SDK
        // (Variáveis de ambiente, arquivos de credenciais locais, perfil IAM da instância, etc.)
        return S3Client.builder()
                .region(Region.of(region))
                .build();
    }
}