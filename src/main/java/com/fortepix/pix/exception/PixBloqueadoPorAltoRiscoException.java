package com.fortepix.pix.exception;

/**
 * Exceção lançada quando o motor antifraude classifica a transação como ALTO_RISCO.
 */
public class PixBloqueadoPorAltoRiscoException extends RuntimeException {

    public PixBloqueadoPorAltoRiscoException(String message) {
        super(message);
    }

    public PixBloqueadoPorAltoRiscoException(String message, Throwable cause) {
        super(message, cause);
    }
}
