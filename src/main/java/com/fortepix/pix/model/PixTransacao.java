package com.fortepix.pix.model;

import com.fortepix.pix.enums.StatusPix;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "pix_transacoes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PixTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_externo", unique = true)
    private String idExterno;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id")
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    private Conta contaDestino;

    @Column(nullable = false)
    private BigDecimal valor;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusPix status;

    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataAtualizacao;
    private OffsetDateTime dataConclusao;
}
