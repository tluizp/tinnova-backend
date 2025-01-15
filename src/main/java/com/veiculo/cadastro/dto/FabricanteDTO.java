package com.veiculo.cadastro.dto;

public class FabricanteDTO {
    private String marca;
    private long quantidade;

    public FabricanteDTO(String marca, long quantidade) {
        this.marca = marca;
        this.quantidade = quantidade;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }
}
