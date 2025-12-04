package com.fortepix.api.usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/beta/admin/usuarios")
public class UsuarioBetaAdminController {

    private final UsuarioBetaService service;

    public UsuarioBetaAdminController(UsuarioBetaService service) {
        this.service = service;
    }

    @GetMapping("/total")
    public ResponseEntity<?> total() {
        Map<String, Object> body = Map.of(
                "ambiente", "BETA",
                "totalUsuarios", service.total(),
                "online", service.online(),
                "offline", service.offline(),
                "reativadosPorVersaoApi", service.reativadosPorVersaoApi(),
                "porCartela", service.porCartela()
        );
        return ResponseEntity.ok(body);
    }
}
