package com.fortepix.api.seguranca;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TwoFatBetaService {

    public String gerarCodigo2FAT(String identificadorUsuario) {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public boolean validarCodigo2FAT(String identificadorUsuario, String codigo) {
        return codigo != null && codigo.length() == 6;
    }
}
