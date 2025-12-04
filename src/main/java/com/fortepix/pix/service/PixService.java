package com.fortepix.pix.service;

import com.fortepix.pix.model.TransacaoPix;
import com.fortepix.pix.dto.PixAnaliseResponse;
import com.fortepix.pix.dto.PixConfirmacaoResponse;
import com.fortepix.pix.dto.PixIniciarRequest;
import com.fortepix.pix.repository.TransacaoPixRepository;
import com.fortepix.pix.service.AntifraudeService;
import com.fortepix.pix.service.CruzamentoDadosService;
import com.fortepix.pix.service.LogAntifraudeService;
import com.fortepix.pix.service.TwoFATService;
import com.fortepix.pix.service.AntifraudeService.ResultadoRisco;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.LocalDateTime;

@Service
public class PixService {

    private final AntifraudeService antifraudeService;
    private final TwoFATService twoFATService;
    private final CruzamentoDadosService cruzamentoDadosService;
    private final TransacaoPixRepository transacaoPixRepository;
    private final LogAntifraudeService logAntifraudeService;

    public PixService(AntifraudeService antifraudeService,
            TwoFATService twoFATService,
            CruzamentoDadosService cruzamentoDadosService,
            TransacaoPixRepository transacaoPixRepository,
            LogAntifraudeService logAntifraudeService) {
        this.antifraudeService = antifraudeService;
        this.twoFATService = twoFATService;
        this.cruzamentoDadosService = cruzamentoDadosService;
        this.transacaoPixRepository = transacaoPixRepository;
        this.logAntifraudeService = logAntifraudeService;
    }

    public PixAnaliseResponse iniciarPix(String documentoUsuario,
            String usuarioId,
            PixIniciarRequest req) {

        // 1) Score via cruzamento de dados
        int score = cruzamentoDadosService.calcularScoreUsuario(
                documentoUsuario,
                req.getChaveDestino(),
                req.getValor());

        // 2) Resultado antifraude
        ResultadoRisco risco = antifraudeService.avaliar(
                req.getChaveDestino(),
                req.getValor(),
                score);

        // 3) Registrar LOG ANTIFRAUDE (sempre)
        logAntifraudeService.registrar(
                documentoUsuario,
                req.getChaveDestino(),
                req.getValor(),
                risco.name(), // APROVADO / BLOQUEIO_PREVENTIVO / ALTO_RISCO
                score,
                "API_JAVA");

        // 4) Tratar cenários de alto risco / bloqueio
        if (risco == ResultadoRisco.ALTO_RISCO) {
            return PixAnaliseResponse.bloqueadoAltoRisco();
        }

        if (risco == ResultadoRisco.BLOQUEIO_PREVENTIVO) {
            return PixAnaliseResponse.bloqueioPreventivo();
        }

        // 5) Aprovado para 2FAT: criar transação e gerar código
        TransacaoPix tx = new TransacaoPix();
        tx.setChaveDestino(req.getChaveDestino());
        tx.setValor(req.getValor());
        tx.setStatus("AGUARDANDO_2FAT");
        tx.setCriadaEm(OffsetDateTime.now());
        tx.setAtualizadaEm(OffsetDateTime.now());
        tx.setDocumentoUsuario(documentoUsuario);
        tx = transacaoPixRepository.save(tx);

        String codigo = twoFATService.gerarCodigo(usuarioId);
        // TODO: integrar envio de SMS/email/push com o código 2FAT.

        return PixAnaliseResponse.aprovado2FAT(tx.getId());
    }

    public PixConfirmacaoResponse confirmarPix(String usuarioId,
            String documentoUsuario,
            Long transacaoId,
            String codigo2FAT,
            String hashBiometricoValido,
            String hashBiometricoRecebido) {

        var opt = transacaoPixRepository.findById(transacaoId);
        if (opt.isEmpty()) {
            return PixConfirmacaoResponse.erro("Transação não encontrada");
        }
        TransacaoPix tx = opt.get();

        if (!"AGUARDANDO_2FAT".equals(tx.getStatus())) {
            return PixConfirmacaoResponse.erro("Transação em estado inválido");
        }

        boolean codigoValido = twoFATService.validarCodigo(usuarioId, codigo2FAT);
        if (!codigoValido) {
            return PixConfirmacaoResponse.erro("2FAT inválido ou expirado");
        }

        // Biometria desativada nesta fase de estabilização estrutural.

        // Aqui entraria a chamada real ao PSP/BaaS do Pix.
        tx.setStatus("CONCLUIDA");
        tx.setAtualizadaEm(OffsetDateTime.now());
        transacaoPixRepository.save(tx);

        return PixConfirmacaoResponse.sucesso();
    }
}
