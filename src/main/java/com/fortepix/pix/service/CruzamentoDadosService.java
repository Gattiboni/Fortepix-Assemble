package com.fortepix.seguranca;

import java.math.BigDecimal;
import java.util.Map;

public class CruzamentoDadosService {

    private final Map<String, Integer> historicoScores;
    private final Map<String, Integer> historicoTentativasFraude;

    public CruzamentoDadosService(Map<String, Integer> historicoScores,
                                  Map<String, Integer> historicoTentativasFraude) {
        this.historicoScores = historicoScores;
        this.historicoTentativasFraude = historicoTentativasFraude;
    }

    public int calcularScoreUsuario(String documento, String chaveDestino, BigDecimal valor) {
        int base = historicoScores.getOrDefault(documento, 50);
        int tentativas = historicoTentativasFraude.getOrDefault(documento, 0);

        int ajusteFraude = -tentativas * 10;
        int ajusteValor = (valor != null && valor.compareTo(new BigDecimal("5000")) > 0) ? -10 : 0;

        int scoreFinal = base + ajusteFraude + ajusteValor;

        if (scoreFinal < 0) scoreFinal = 0;
        if (scoreFinal > 100) scoreFinal = 100;

        return scoreFinal;
    }
}
