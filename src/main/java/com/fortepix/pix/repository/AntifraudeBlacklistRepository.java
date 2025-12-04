package com.fortepix.pix.repository;

import com.fortepix.pix.model.AntifraudeBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AntifraudeBlacklistRepository extends JpaRepository<AntifraudeBlacklist, Long> {

    Optional<AntifraudeBlacklist> findFirstByTipoAndValorAndAtivoTrue(String tipo, String valor);
}
