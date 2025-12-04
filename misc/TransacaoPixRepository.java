package com.fortepix.repositorio;

import com.fortepix.dominio.TransacaoPix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoPixRepository extends JpaRepository<TransacaoPix, Long> {
}
