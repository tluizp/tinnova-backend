package com.veiculo.cadastro.dto;

public class DecadaDTO {

    private int decada;
    private long quantidade;

    public DecadaDTO(int decada, long quantidade) {
        this.decada = decada;
        this.quantidade = quantidade;
    }

    public int getDecada() {
        return decada;
    }

    public void setDecada(int decada) {
        this.decada = decada;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }
}
