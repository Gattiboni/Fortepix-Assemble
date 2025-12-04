package com.fortepix.api.security;

import com.fortepix.pix.model.Usuario;
import com.fortepix.pix.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        String perfil = usuario.getPerfil() != null ? usuario.getPerfil() : "CLIENTE";
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + perfil.toUpperCase());

        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                Collections.singletonList(authority)
        );
    }
}
