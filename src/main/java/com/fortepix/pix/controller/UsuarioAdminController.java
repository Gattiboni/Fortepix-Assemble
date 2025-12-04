package com.fortepix.api.usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/usuarios")
public class UsuarioAdminController {

    private final UsuarioService service;

    public UsuarioAdminController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/total")
    public ResponseEntity<?> total() {
        Map<String, Object> body = Map.of(
                "totalUsuarios", service.total(),
                "online", service.online(),
                "offline", service.offline(),
                "reativadosPorVersaoApi", service.reativadosPorVersaoApi(),
                "porCartela", service.porCartela()
        );
        return ResponseEntity.ok(body);
    }
}
