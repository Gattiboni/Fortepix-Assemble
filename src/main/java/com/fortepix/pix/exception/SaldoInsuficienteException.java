package com.fortepix.pix.exception;

/**
 * Exceção para indicar saldo insuficiente na conta de origem.
 */
public class SaldoInsuficienteException extends RuntimeException {

    public SaldoInsuficienteException(String message) {
        super(message);
    }

    public SaldoInsuficienteException(String message, Throwable cause) {
        super(message, cause);
    }
}
