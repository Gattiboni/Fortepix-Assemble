package com.fortepix.pix.service;

import com.fortepix.pix.model.LogAntifraude;
import com.fortepix.pix.repository.LogAntifraudeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AntifraudeAdminService {

        private final LogAntifraudeRepository repository;

        public AntifraudeAdminService(LogAntifraudeRepository repository) {
                this.repository = repository;
        }

        public List<LogAntifraude> buscarLogs(String documento,
                        String chaveDestino,
                        String resultadoRisco,
                        LocalDate dataInicio,
                        LocalDate dataFim) {

                List<LogAntifraude> todos = repository.findAll();

                return filtrar(todos, documento, chaveDestino, resultadoRisco, dataInicio, dataFim);
        }

        public Map<String, Long> statsPorResultado(String documento,
                        String chaveDestino,
                        LocalDate dataInicio,
                        LocalDate dataFim) {

                List<LogAntifraude> filtrados = filtrar(
                                repository.findAll(),
                                documento,
                                chaveDestino,
                                null,
                                dataInicio,
                                dataFim);

                return filtrados.stream()
                                .collect(Collectors.groupingBy(
                                                log -> Optional.ofNullable(log.getResultadoRisco())
                                                                .orElse("DESCONHECIDO"),
                                                Collectors.counting()));
        }

        public Map<LocalDate, Long> statsPorDia(LocalDate dataInicio,
                        LocalDate dataFim) {

                List<LogAntifraude> todos = repository.findAll();
                List<LogAntifraude> filtrados = filtrar(todos, null, null, null, dataInicio, dataFim);

                return filtrados.stream()
                                .collect(Collectors.groupingBy(
                                                log -> log.getCriadoEm().toLocalDate(),
                                                TreeMap::new,
                                                Collectors.counting()));
        }

        public String gerarCsv(String documento,
                        String chaveDestino,
                        String resultadoRisco,
                        LocalDate dataInicio,
                        LocalDate dataFim) {

                List<LogAntifraude> filtrados = filtrar(
                                repository.findAll(),
                                documento,
                                chaveDestino,
                                resultadoRisco,
                                dataInicio,
                                dataFim);

                StringBuilder sb = new StringBuilder();
                sb.append("id,documentoUsuario,chaveDestino,valor,resultadoRisco,scoreCalculado,criadoEm,origem\n");
                for (LogAntifraude log : filtrados) {
                        sb.append(log.getId()).append(",");
                        sb.append(safe(log.getDocumentoUsuario())).append(",");
                        sb.append(safe(log.getChaveDestino())).append(",");
                        sb.append(log.getValor() != null ? log.getValor() : "").append(",");
                        sb.append(safe(log.getResultadoRisco())).append(",");
                        sb.append(log.getScoreCalculado() != null ? log.getScoreCalculado() : "").append(",");
                        sb.append(log.getCriadoEm() != null ? log.getCriadoEm() : "").append(",");
                        sb.append(safe(log.getOrigem())).append("\n");
                }
                return sb.toString();
        }

        private List<LogAntifraude> filtrar(List<LogAntifraude> base,
                        String documento,
                        String chaveDestino,
                        String resultadoRisco,
                        LocalDate dataInicio,
                        LocalDate dataFim) {

                return base.stream()
                                .filter(log -> documento == null || (log.getDocumentoUsuario() != null
                                                && log.getDocumentoUsuario().equals(documento)))
                                .filter(log -> chaveDestino == null || (log.getChaveDestino() != null
                                                && log.getChaveDestino().equals(chaveDestino)))
                                .filter(log -> resultadoRisco == null || (log.getResultadoRisco() != null
                                                && log.getResultadoRisco().equalsIgnoreCase(resultadoRisco)))
                                .filter(log -> {
                                        if (dataInicio == null && dataFim == null)
                                                return true;
                                        OffsetDateTime criadoEm = log.getCriadoEm();
                                        if (criadoEm == null)
                                                return false;
                                        LocalDate data = criadoEm.toLocalDate();
                                        if (dataInicio != null && data.isBefore(dataInicio))
                                                return false;
                                        if (dataFim != null && data.isAfter(dataFim))
                                                return false;
                                        return true;
                                })
                                .collect(Collectors.toList());
        }

        private String safe(String s) {
                return s == null ? "" : s.replace(",", " ");
        }
}
