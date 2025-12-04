package com.fortepix.pix.service;

import com.fortepix.pix.model.LogAntifraude;
import com.fortepix.pix.repository.LogAntifraudeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class LogAntifraudeService {

    private final LogAntifraudeRepository repository;

    public LogAntifraudeService(LogAntifraudeRepository repository) {
        this.repository = repository;
    }

    public void registrar(String documentoUsuario,
            String chaveDestino,
            BigDecimal valor,
            String resultadoRisco,
            int scoreCalculado,
            String origem) {

        LogAntifraude log = new LogAntifraude();
        log.setDocumentoUsuario(documentoUsuario);
        log.setChaveDestino(chaveDestino);
        log.setValor(valor);
        log.setResultadoRisco(resultadoRisco);
        log.setScoreCalculado(scoreCalculado);
        log.setOrigem(origem);
        log.setCriadoEm(OffsetDateTime.now());

        repository.save(log);
    }
}
