package com.veiculo.cadastro.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum Marcas {
    TOYOTA,
    MERCEDES,
    BMW,
    TESLA,
    HONDA,
    HYUNDAI,
    PORSCHE,
    AUDI,
    VOLKSWAGEN,
    FORD,
    NISSAN,
    MITSUBISHI,
    MAZDA;

    public static Optional<Marcas> verificaMarca(String marca) {
        return Arrays.stream(Marcas.values()).filter(marcas ->
                Objects.equals(marcas.name().toLowerCase(), marca.toLowerCase())).findFirst();
    }
}
