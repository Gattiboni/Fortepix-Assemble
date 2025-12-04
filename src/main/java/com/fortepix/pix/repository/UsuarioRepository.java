package com.fortepix.api.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    long countByStatus(String status);
    long countByReativadoPorVersaoApi(boolean flag);
    long countByCartela(String cartela);
}
