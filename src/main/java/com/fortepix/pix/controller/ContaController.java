package com.fortepix.pix.controller;

import com.fortepix.pix.model.Conta;
import com.fortepix.pix.model.Usuario;
import com.fortepix.pix.repository.ContaRepository;
import com.fortepix.pix.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/criar/{usuarioId}")
    public ResponseEntity<Conta> criarContaParaUsuario(@PathVariable Long usuarioId,
                                                       @RequestBody Conta contaRequest) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para ID " + usuarioId));

        Conta conta = new Conta();
        conta.setUsuario(usuario);
        conta.setChavePix(contaRequest.getChavePix());
        conta.setSaldo(contaRequest.getSaldo() != null ? contaRequest.getSaldo() : BigDecimal.ZERO);

        Conta salva = contaRepository.save(conta);
        return ResponseEntity.ok(salva);
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listar() {
        return ResponseEntity.ok(contaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id) {
        return contaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
