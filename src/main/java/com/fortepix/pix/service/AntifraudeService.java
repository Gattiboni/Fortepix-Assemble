package com.fortepix.pix.service;

import java.math.BigDecimal;
import java.util.Set;

public class AntifraudeService {

    private final Set<String> chavesAltoRisco;
    private final BigDecimal LIMITE_BLOQUEIO_PREVENTIVO;
    private final BigDecimal LIMITE_ALTO_RISCO;

    public enum ResultadoRisco {
        APROVADO,
        BLOQUEIO_PREVENTIVO,
        ALTO_RISCO
    }

    public AntifraudeService(Set<String> chavesAltoRisco,
            BigDecimal limiteBloqueioPreventivo,
            BigDecimal limiteAltoRisco) {
        this.chavesAltoRisco = chavesAltoRisco;
        this.LIMITE_BLOQUEIO_PREVENTIVO = limiteBloqueioPreventivo;
        this.LIMITE_ALTO_RISCO = limiteAltoRisco;
    }

    public ResultadoRisco avaliar(String chaveDestino, BigDecimal valor, int scoreUsuario) {
        if (chavesAltoRisco.contains(chaveDestino)) {
            return ResultadoRisco.ALTO_RISCO;
        }
        if (valor != null && valor.compareTo(LIMITE_ALTO_RISCO) > 0) {
            return ResultadoRisco.ALTO_RISCO;
        }
        if (valor != null && valor.compareTo(LIMITE_BLOQUEIO_PREVENTIVO) > 0) {
            return ResultadoRisco.BLOQUEIO_PREVENTIVO;
        }
        if (scoreUsuario < 40) {
            return ResultadoRisco.BLOQUEIO_PREVENTIVO;
        }
        return ResultadoRisco.APROVADO;
    }
}
