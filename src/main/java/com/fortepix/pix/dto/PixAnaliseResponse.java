package com.fortepix.pix.dto;

public class PixAnaliseResponse {

    public enum Status {
        BLOQUEADO_ALTO_RISCO,
        BLOQUEIO_PREVENTIVO,
        APROVADO_2FAT
    }

    private Status status;
    private String mensagem;
    private Long transacaoId;

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public Long getTransacaoId() { return transacaoId; }
    public void setTransacaoId(Long transacaoId) { this.transacaoId = transacaoId; }

    public static PixAnaliseResponse bloqueadoAltoRisco() {
        PixAnaliseResponse r = new PixAnaliseResponse();
        r.status = Status.BLOQUEADO_ALTO_RISCO;
        r.mensagem = "Transação identificada como alto risco. Bloqueada.";
        return r;
    }

    public static PixAnaliseResponse bloqueioPreventivo() {
        PixAnaliseResponse r = new PixAnaliseResponse();
        r.status = Status.BLOQUEIO_PREVENTIVO;
        r.mensagem = "Transação em bloqueio preventivo. Revise os dados.";
        return r;
    }

    public static PixAnaliseResponse aprovado2FAT(Long transacaoId) {
        PixAnaliseResponse r = new PixAnaliseResponse();
        r.status = Status.APROVADO_2FAT;
        r.mensagem = "Transação aprovada para 2FAT. Código enviado.";
        r.transacaoId = transacaoId;
        return r;
    }
}
