package com.fortepix.dominio;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "logs_antifraude")
public class LogAntifraude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentoUsuario;
    private String chaveDestino;
    private BigDecimal valor;
    private String resultadoRisco;
    private Integer scoreCalculado;
    private OffsetDateTime criadoEm;
    private String origem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
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

    public String getResultadoRisco() {
        return resultadoRisco;
    }

    public void setResultadoRisco(String resultadoRisco) {
        this.resultadoRisco = resultadoRisco;
    }

    public Integer getScoreCalculado() {
        return scoreCalculado;
    }

    public void setScoreCalculado(Integer scoreCalculado) {
        this.scoreCalculado = scoreCalculado;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(OffsetDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }
}
