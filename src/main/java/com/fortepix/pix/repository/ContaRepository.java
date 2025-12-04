package com.fortepix.pix.repository;

import com.fortepix.pix.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByUsuarioId(Long usuarioId);

    Optional<Conta> findByChavePix(String chavePix);
}
