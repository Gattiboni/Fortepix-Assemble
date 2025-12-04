package com.fortepix.api.usuario;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public long total() { return repo.count(); }

    public long online() { return repo.countByStatus("ONLINE"); }

    public long offline() { return repo.countByStatus("OFFLINE"); }

    public long reativadosPorVersaoApi() { return repo.countByReativadoPorVersaoApi(true); }

    public Map<String, Long> porCartela() {
        Map<String, Long> mapa = new HashMap<>();
        String[] cartelas = new String[] {
                "CLIENTE_COMUM",
                "CLIENTE_SEGURANCA_ALTA",
                "CLIENTE_EMPRESA",
                "CLIENTE_TESTE",
                "CLIENTE_BLOQUEADO"
        };
        for (String c : cartelas) {
            mapa.put(c, repo.countByCartela(c));
        }
        return mapa;
    }
}
