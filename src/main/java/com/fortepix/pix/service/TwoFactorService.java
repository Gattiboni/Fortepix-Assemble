package com.fortepix.pix.service.seguranca;

import com.fortepix.pix.model.PixTransacao;
import com.fortepix.pix.model.Usuario;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela validação de 2FAT / biometria.
 * Stub inicial: não faz nenhuma validação real, apenas representa o ponto de integração.
 */
@Service
public class TwoFactorService {

    public void validar2FatorSeNecessario(Usuario usuarioAutenticado, PixTransacao transacao) {
        // TODO: implementar envio e validação real de 2FAT + biometria.
        // Neste stub, considera-se que o segundo fator já foi validado.
    }
}
