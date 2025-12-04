package com.fortepix.api.pix;

import java.math.BigDecimal;

public class PixBetaRequest {
    private String chaveDestino;
    private BigDecimal valor;
    private String descricao;

    public String getChaveDestino() { return chaveDestino; }
    public void setChaveDestino(String chaveDestino) { this.chaveDestino = chaveDestino; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
