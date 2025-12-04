package com.fortepix.pix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fortepix.pix.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    java.util.Optional<Usuario> findByEmail(String email);

    long countByStatus(String status);

    long countByReativadoPorVersaoApi(boolean flag);

    long countByCartela(String cartela);
}
