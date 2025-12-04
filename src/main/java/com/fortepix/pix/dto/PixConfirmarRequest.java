package com.fortepix.pix.dto;

public class PixConfirmarRequest {
    private Long transacaoId;
    private String codigo2FAT;
    private String hashBiometricoValido;
    private String hashBiometricoRecebido;

    public Long getTransacaoId() { return transacaoId; }
    public void setTransacaoId(Long transacaoId) { this.transacaoId = transacaoId; }

    public String getCodigo2FAT() { return codigo2FAT; }
    public void setCodigo2FAT(String codigo2FAT) { this.codigo2FAT = codigo2FAT; }

    public String getHashBiometricoValido() { return hashBiometricoValido; }
    public void setHashBiometricoValido(String hashBiometricoValido) { this.hashBiometricoValido = hashBiometricoValido; }

    public String getHashBiometricoRecebido() { return hashBiometricoRecebido; }
    public void setHashBiometricoRecebido(String hashBiometricoRecebido) { this.hashBiometricoRecebido = hashBiometricoRecebido; }
}
