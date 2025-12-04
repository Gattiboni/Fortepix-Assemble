package com.fortepix.pix.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "transacoes_pix")
public class TransacaoPix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chaveDestino;
    private BigDecimal valor;
    private String status;
    private OffsetDateTime criadaEm;
    private OffsetDateTime atualizadaEm;
    private String documentoUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChaveDestino() {
        return chaveDestino;
    }

    public void setChaveDestino(String chaveDestino) {
        this.chaveDestino = chaveDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OffsetDateTime getCriadaEm() {
        return criadaEm;
    }

    public void setCriadaEm(OffsetDateTime criadaEm) {
        this.criadaEm = criadaEm;
    }

    public OffsetDateTime getAtualizadaEm() {
        return atualizadaEm;
    }

    public void setAtualizadaEm(OffsetDateTime atualizadaEm) {
        this.atualizadaEm = atualizadaEm;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }
}
