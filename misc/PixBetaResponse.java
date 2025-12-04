package com.fortepix.api.pix;

import java.math.BigDecimal;

public class PixBetaResponse {
    private String status;
    private String motivo;
    private BigDecimal valor;
    private String ambiente;

    public PixBetaResponse() {}

    public PixBetaResponse(String status, String motivo, BigDecimal valor, String ambiente) {
        this.status = status;
        this.motivo = motivo;
        this.valor = valor;
        this.ambiente = ambiente;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getAmbiente() { return ambiente; }
    public void setAmbiente(String ambiente) { this.ambiente = ambiente; }
}
