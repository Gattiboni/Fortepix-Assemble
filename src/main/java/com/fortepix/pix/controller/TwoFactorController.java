package com.fortepix.pix.controller;

import com.fortepix.pix.dto.PixResponseDTO;
import com.fortepix.pix.model.PixTransacao;
import com.fortepix.pix.model.Usuario;
import com.fortepix.pix.repository.PixTransacaoRepository;
import com.fortepix.pix.repository.UsuarioRepository;
import com.fortepix.pix.service.PixService;
import com.fortepix.pix.service.seguranca.TwoFactorService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller dedicado ao fluxo de 2FAT.
 *
 *  - Inicia o envio do código 2FAT para uma transação específica
 *  - Confirma o código recebido e, em seguida, conclui o Pix
 */
@RestController
@RequestMapping("/api/2fat")
@RequiredArgsConstructor
public class TwoFactorController {

    private final TwoFactorService twoFactorService;
    private final UsuarioRepository usuarioRepository;
    private final PixTransacaoRepository pixTransacaoRepository;
    private final PixService pixService;

    @PostMapping("/iniciar")
    public ResponseEntity<String> iniciar(@RequestBody TwoFactorRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        PixTransacao transacao = pixTransacaoRepository.findByIdExterno(request.getIdExternoTransacao())
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada para o ID externo informado."));

        twoFactorService.iniciar2Fator(usuario, transacao);

        return ResponseEntity.ok("Fluxo 2FAT iniciado. Código enviado para o canal configurado (simulado).");
    }

    @PostMapping("/confirmar")
    public ResponseEntity<PixResponseDTO> confirmar(@RequestBody TwoFactorConfirmRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        PixTransacao transacao = pixTransacaoRepository.findByIdExterno(request.getIdExternoTransacao())
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada para o ID externo informado."));

        // 1) Valida o código 2FAT
        twoFactorService.confirmar2Fator(usuario, transacao, request.getCodigo());

        // 2) Conclui o Pix (débito/crédito) após validação
        PixResponseDTO resposta = pixService.concluirPixPos2Fator(transacao);

        return ResponseEntity.ok(resposta);
    }

    @Data
    public static class TwoFactorRequest {
        private Long usuarioId;
        private String idExternoTransacao;
    }

    @Data
    public static class TwoFactorConfirmRequest {
        private Long usuarioId;
        private String idExternoTransacao;
        private String codigo;
    }
}
