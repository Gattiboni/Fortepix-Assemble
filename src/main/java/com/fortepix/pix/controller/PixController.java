package com.fortepix.pix.controller;

import com.fortepix.pix.service.AntifraudeService;
import com.fortepix.pix.dto.PixRequest;
import com.fortepix.pix.dto.PixResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pix")
public class PixController {

    private final AntifraudeService antifraudeService;

    public PixController(AntifraudeService antifraudeService) {
        this.antifraudeService = antifraudeService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<PixResponse> enviarPix(@RequestBody PixRequest request) {
        var risco = antifraudeService.avaliar(request.getChaveDestino(), request.getValor(), 50);

        if (risco == AntifraudeService.ResultadoRisco.ALTO_RISCO) {
            return ResponseEntity.ok(new PixResponse("NEGADO", "ALTO_RISCO_ANTIFRAUDE", request.getValor()));
        }
        if (risco == AntifraudeService.ResultadoRisco.BLOQUEIO_PREVENTIVO) {
            return ResponseEntity.ok(new PixResponse("EM_ANALISE", "BLOQUEIO_PREVENTIVO", request.getValor()));
        }

        return ResponseEntity.ok(new PixResponse("APROVADO", "OK", request.getValor()));
    }
}
