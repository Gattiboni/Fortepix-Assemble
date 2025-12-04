package com.fortepix.pix.dto;

import com.fortepix.pix.enums.StatusPix;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class PixResponseDTO {

    private String idExterno;
    private StatusPix status;
    private BigDecimal valor;
    private String chaveDestino;
    private String descricao;
    private OffsetDateTime dataConclusao;
}
