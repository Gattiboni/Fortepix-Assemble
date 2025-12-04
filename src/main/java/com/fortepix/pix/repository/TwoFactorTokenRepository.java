package com.fortepix.pix.repository;

import com.fortepix.pix.model.TwoFactorToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TwoFactorTokenRepository extends JpaRepository<TwoFactorToken, Long> {

    Optional<TwoFactorToken> findFirstByUsuarioIdAndIdExternoTransacaoAndUsadoFalseOrderByDataCriacaoDesc(
            Long usuarioId,
            String idExternoTransacao
    );
}
