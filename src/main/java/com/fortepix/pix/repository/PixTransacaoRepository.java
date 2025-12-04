package com.fortepix.pix.repository;

import com.fortepix.pix.model.PixTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PixTransacaoRepository extends JpaRepository<PixTransacao, Long> {

    Optional<PixTransacao> findByIdExterno(String idExterno);
}
