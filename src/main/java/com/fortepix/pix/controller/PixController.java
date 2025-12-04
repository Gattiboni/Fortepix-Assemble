package com.fortepix.api.pix;

import com.fortepix.api.seguranca.TwoFatService;
import com.fortepix.api.seguranca.BiometriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pix")
public class PixController {

    private final AntifraudeService antifraudeService;
    private final TwoFatService twoFatService;
    private final BiometriaService biometriaService;

    public PixController(AntifraudeService antifraudeService,
                         TwoFatService twoFatService,
                         BiometriaService biometriaService) {
        this.antifraudeService = antifraudeService;
        this.twoFatService = twoFatService;
        this.biometriaService = biometriaService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<PixResponse> enviarPix(@RequestBody PixRequest request) {
        var risco = antifraudeService.avaliarRisco(request.getChaveDestino(), request.getValor());

        if (risco == AntifraudeService.ResultadoRisco.ALTO_RISCO) {
            return ResponseEntity.ok(new PixResponse("NEGADO", "ALTO_RISCO_ANTIFRAUDE", request.getValor()));
        }
        if (risco == AntifraudeService.ResultadoRisco.BLOQUEIO_PREVENTIVO) {
            return ResponseEntity.ok(new PixResponse("EM_ANALISE", "BLOQUEIO_PREVENTIVO", request.getValor()));
        }

        return ResponseEntity.ok(new PixResponse("APROVADO", "OK", request.getValor()));
    }
}
