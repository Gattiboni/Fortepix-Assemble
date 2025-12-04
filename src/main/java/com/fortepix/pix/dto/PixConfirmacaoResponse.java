package com.fortepix.pix.dto;

public class PixConfirmacaoResponse {

    private String status;
    private String mensagem;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public static PixConfirmacaoResponse sucesso() {
        PixConfirmacaoResponse r = new PixConfirmacaoResponse();
        r.status = "SUCESSO";
        r.mensagem = "Pix confirmado com sucesso após 2FAT + Biometria.";
        return r;
    }

    public static PixConfirmacaoResponse erro(String msg) {
        PixConfirmacaoResponse r = new PixConfirmacaoResponse();
        r.status = "ERRO";
        r.mensagem = msg;
        return r;
    }
}
