package com.fortepix.pix.exception;

/**
 * Exceção lançada quando o motor antifraude classifica a transação como BLOQUEIO_PREVENTIVO.
 */
public class PixBloqueioPreventivoException extends RuntimeException {

    public PixBloqueioPreventivoException(String message) {
        super(message);
    }

    public PixBloqueioPreventivoException(String message, Throwable cause) {
        super(message, cause);
    }
}
