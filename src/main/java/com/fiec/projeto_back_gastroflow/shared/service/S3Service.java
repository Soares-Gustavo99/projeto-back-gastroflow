package com.fiec.projeto_back_gastroflow.shared.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;

    // Injeta o nome do bucket do application.properties
    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Faz o upload de um arquivo para o S3.
     * @param file O arquivo MultipartFile recebido do endpoint.
     * @return A chave (nome) do arquivo salvo no S3.
     */
    public String uploadFile(MultipartFile file) throws IOException {

        // 1. Gera um nome de arquivo único para evitar colisões
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String keyName = UUID.randomUUID().toString() + fileExtension;

        // 2. Cria a requisição de PutObject
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        // 3. Executa o upload
        s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return keyName;
    }

    // Você pode adicionar métodos para download, exclusão, etc.
}