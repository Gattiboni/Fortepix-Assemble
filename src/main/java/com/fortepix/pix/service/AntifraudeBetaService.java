package com.fortepix.api.pix;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AntifraudeBetaService {

    public enum ResultadoRisco { APROVADO, BLOQUEIO_PREVENTIVO, ALTO_RISCO }

    public ResultadoRisco avaliarRisco(String chaveDestino, BigDecimal valor) {
        if (valor != null && valor.compareTo(new BigDecimal("5000")) > 0) {
            return ResultadoRisco.BLOQUEIO_PREVENTIVO;
        }
        if (chaveDestino != null && chaveDestino.endsWith("@risco")) {
            return ResultadoRisco.ALTO_RISCO;
        }
        return ResultadoRisco.APROVADO;
    }
}
