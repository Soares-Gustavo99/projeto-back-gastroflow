package com.fiec.projeto_back_gastroflow.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe de utilidade para criptografar e verificar senhas de forma segura.
 * Usa o algoritmo BCrypt, padrão da indústria para hashing de senhas.
 */

public final class PasswordEncryptor {

    // Instância única do PasswordEncoder, thread-safe.
    private static PasswordEncoder passwordEncoder;

    public static PasswordEncoder getInstance(){
        if(passwordEncoder == null){
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder;
    }
    /**
     * Construtor privado para evitar a instanciação da classe de utilidade.
     */
    private PasswordEncryptor() {
        // Nada a fazer
    }

    /**
     * Criptografa uma senha em texto plano.
     *
     * @param rawPassword A senha em texto plano a ser criptografada.
     * @return A senha criptografada.
     * @throws IllegalArgumentException se a senha for nula.
     */
    public static String encrypt(String rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("A senha não pode ser nula.");
        }
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Verifica se uma senha em texto plano corresponde a uma senha criptografada.
     *
     * @param rawPassword     A senha em texto plano.
     * @param encodedPassword A senha criptografada armazenada.
     * @return {@code true} se as senhas coincidirem, {@code false} caso contrário.
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}