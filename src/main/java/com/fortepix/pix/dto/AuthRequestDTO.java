package com.fortepix.pix.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDTO {

    @Email(message = "Informe um e-mail válido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;
}
