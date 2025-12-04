package com.fortepix.pix.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String status;
    private String cartela;
    private boolean reativadoPorVersaoApi;
    private String perfil;
    private String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCartela() {
        return cartela;
    }

    public void setCartela(String cartela) {
        this.cartela = cartela;
    }

    public boolean isReativadoPorVersaoApi() {
        return reativadoPorVersaoApi;
    }

    public void setReativadoPorVersaoApi(boolean reativadoPorVersaoApi) {
        this.reativadoPorVersaoApi = reativadoPorVersaoApi;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
