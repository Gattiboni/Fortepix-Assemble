package com.fortepix.pix.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "contas")
@Data
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "chave_pix", unique = true, nullable = false)
    private String chavePix;

    @Column(nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;
}
