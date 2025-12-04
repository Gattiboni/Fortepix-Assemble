package com.fortepix.pix.dto;

import java.math.BigDecimal;

public class PixResponse {
    private String status;
    private String motivo;
    private BigDecimal valor;

    public PixResponse() {
    }

    public PixResponse(String status, String motivo, BigDecimal valor) {
        this.status = status;
        this.motivo = motivo;
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
