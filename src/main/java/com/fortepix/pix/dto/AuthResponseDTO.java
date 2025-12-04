package com.fortepix.pix.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {

    private String token;

    private String perfil;

    private Long usuarioId;
}
