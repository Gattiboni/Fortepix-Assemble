package com.fortepix.repositorio;

import com.fortepix.dominio.LogAntifraude;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogAntifraudeRepository extends JpaRepository<LogAntifraude, Long> {
}
