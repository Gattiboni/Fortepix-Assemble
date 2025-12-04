package com.fortepix.pix.dto;

import java.math.BigDecimal;

public class PixIniciarRequest {
    private String chaveDestino;
    private BigDecimal valor;
    private boolean dispositivoNovo;

    public String getChaveDestino() { return chaveDestino; }
    public void setChaveDestino(String chaveDestino) { this.chaveDestino = chaveDestino; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public boolean isDispositivoNovo() { return dispositivoNovo; }
    public void setDispositivoNovo(boolean dispositivoNovo) { this.dispositivoNovo = dispositivoNovo; }
}
