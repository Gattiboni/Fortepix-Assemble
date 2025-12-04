package com.fortepix.pix.repository;

import com.fortepix.pix.model.LogAntifraude;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogAntifraudeRepository extends JpaRepository<LogAntifraude, Long> {
}
