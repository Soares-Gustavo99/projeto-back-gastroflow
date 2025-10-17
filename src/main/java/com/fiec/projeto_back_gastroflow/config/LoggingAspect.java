package com.fiec.projeto_back_gastroflow.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // 1. Ponto de Corte (Pointcut)
    // Define onde o Aspecto deve ser aplicado.
    // Neste caso, em todos os métodos públicos de classes no pacote 'controller'
    // que estão anotadas com @RestController ou @Controller.
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) || " +
            "within(@org.springframework.stereotype.Controller *)")
    public void applicationControllerPointcut() {
        // O corpo é intencionalmente vazio, é apenas um placeholder para o Pointcut.
    }

    // 2. Advice 'Around' para Log de Método e Argumentos
    // O @Around permite logar informações ANTES e DEPOIS da execução do método.
    @Around("applicationControllerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        // Log ANTES da execução do método
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info(">>>> Controller INÍCIO: {}.{}()", className, methodName);
        log.info(">>>> Argumentos Passados: {}", Arrays.toString(args));

        try {
            // Executa o método original
            Object result = joinPoint.proceed();

            // Log DEPOIS da execução (com sucesso)
            // Geralmente, o log do retorno em controllers é omitido para não poluir
            // o log com grandes objetos de resposta, mas o final da execução é registrado.
            log.info("<<<< Controller FIM: {}.{}() - SUCESSO", className, methodName);

            return result;
        } catch (IllegalArgumentException e) {
            // Log DEPOIS da execução (com erro)
            log.error("Exception Ilegal: {} em {}.{}()", Arrays.toString(args), className, methodName);
            throw e;
        }
    }
}
