package com.fortepix.api.security;

import com.fortepix.pix.service.AntifraudeService;
import com.fortepix.pix.service.TwoFATService;
import com.fortepix.pix.service.CruzamentoDadosService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

@Configuration
public class SegurancaConfig {

    @Bean
    public AntifraudeService antifraudeService() {
        var chavesAltoRisco = new HashSet<String>();
        return new AntifraudeService(
                chavesAltoRisco,
                new BigDecimal("1000.00"),
                new BigDecimal("5000.00"));
    }

    @Bean
    public TwoFATService twoFATService() {
        return new TwoFATService(180L);
    }

    @Bean
    public CruzamentoDadosService cruzamentoDadosService() {
        return new CruzamentoDadosService(
                new HashMap<>(),
                new HashMap<>());
    }
}
