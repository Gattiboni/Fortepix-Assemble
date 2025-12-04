package com.fortepix.pix.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PixRequestDTO {

    private String chaveDestino;
    private BigDecimal valor;
    private String descricao;
}
