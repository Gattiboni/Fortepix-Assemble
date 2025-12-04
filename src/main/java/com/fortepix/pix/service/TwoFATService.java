package com.fortepix.seguranca;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TwoFATService {

    private final Map<String, RegistroCodigo> codigos = new HashMap<>();
    private final long validadeSegundos;

    public TwoFATService(long validadeSegundos) {
        this.validadeSegundos = validadeSegundos;
    }

    public String gerarCodigo(String identificadorUsuario) {
        String codigo = String.format("%06d", new Random().nextInt(999999));
        RegistroCodigo registro = new RegistroCodigo(codigo, Instant.now());
        codigos.put(identificadorUsuario, registro);
        return codigo;
    }

    public boolean validarCodigo(String identificadorUsuario, String codigoInformado) {
        RegistroCodigo registro = codigos.get(identificadorUsuario);
        if (registro == null) return false;
        if (!registro.codigo().equals(codigoInformado)) return false;
        Instant agora = Instant.now();
        if (agora.isAfter(registro.criadoEm().plusSeconds(validadeSegundos))) {
            codigos.remove(identificadorUsuario);
            return false;
        }
        codigos.remove(identificadorUsuario);
        return true;
    }

    private record RegistroCodigo(String codigo, Instant criadoEm) {}
}
