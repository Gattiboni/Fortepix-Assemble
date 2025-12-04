package com.fortepix.api.pix;

import com.fortepix.api.seguranca.TwoFatBetaService;
import com.fortepix.api.seguranca.BiometriaBetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/beta/api/pix")
public class PixBetaController {

    private final AntifraudeBetaService antifraudeService;
    private final TwoFatBetaService twoFatService;
    private final BiometriaBetaService biometriaService;

    public PixBetaController(AntifraudeBetaService antifraudeService,
                             TwoFatBetaService twoFatService,
                             BiometriaBetaService biometriaService) {
        this.antifraudeService = antifraudeService;
        this.twoFatService = twoFatService;
        this.biometriaService = biometriaService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<PixBetaResponse> enviarPix(@RequestBody PixBetaRequest request) {
        var risco = antifraudeService.avaliarRisco(request.getChaveDestino(), request.getValor());

        if (risco == AntifraudeBetaService.ResultadoRisco.ALTO_RISCO) {
            return ResponseEntity.ok(new PixBetaResponse("NEGADO", "ALTO_RISCO_ANTIFRAUDE", request.getValor(), "BETA"));
        }
        if (risco == AntifraudeBetaService.ResultadoRisco.BLOQUEIO_PREVENTIVO) {
            return ResponseEntity.ok(new PixBetaResponse("EM_ANALISE", "BLOQUEIO_PREVENTIVO", request.getValor(), "BETA"));
        }

        return ResponseEntity.ok(new PixBetaResponse("APROVADO", "OK", request.getValor(), "BETA"));
    }
}
