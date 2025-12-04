package com.fortepix.pix.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name = "antifraude_blacklist")
@Data
public class AntifraudeBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tipo do identificador de risco:
     *  - CPF
     *  - CNPJ
     *  - CHAVE_PIX
     *  - CONTA
     *  - USUARIO
     *  - OUTRO
     */
    @Column(length = 30, nullable = false)
    private String tipo;

    /**
     * Valor do identificador (ex: chave Pix, CPF, CNPJ, ID de conta etc).
     */
    @Column(nullable = false)
    private String valor;

    /**
     * Motivo do cadastro na blacklist.
     */
    private String motivo;

    /**
     * Indica se o registro está ativo.
     */
    private boolean ativo = true;

    /**
     * Data/hora de criação do registro.
     */
    private OffsetDateTime dataCriacao = OffsetDateTime.now();
}
