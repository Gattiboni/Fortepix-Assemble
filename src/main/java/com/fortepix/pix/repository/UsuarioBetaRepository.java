package com.fortepix.api.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioBetaRepository extends JpaRepository<UsuarioBeta, Long> {
    long countByStatus(String status);
    long countByReativadoPorVersaoApi(boolean flag);
    long countByCartela(String cartela);
}
