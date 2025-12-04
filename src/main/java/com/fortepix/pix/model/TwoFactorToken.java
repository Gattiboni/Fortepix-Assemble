package com.fortepix.pix.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Table(name = "twofactor_tokens")
@Data
public class TwoFactorToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "id_externo_transacao", nullable = false)
    private String idExternoTransacao;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private OffsetDateTime expiracao;

    @Column(nullable = false)
    private boolean usado = false;

    private OffsetDateTime dataCriacao = OffsetDateTime.now();
}
