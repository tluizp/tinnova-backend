package com.veiculo.cadastro.repository;

import com.veiculo.cadastro.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    // Contar veículos por década de fabricação
    @Query("SELECT (v.ano / 10) * 10 AS decada, COUNT(v) AS quantidade FROM Veiculo v GROUP BY decada ORDER BY decada")
    List<Object[]> findVeiculosPorDecada();

    // Contar veículos por fabricante
    @Query("SELECT v.marca AS marca, COUNT(v) AS quantidade FROM Veiculo v GROUP BY v.marca ORDER BY quantidade DESC")
    List<Object[]> findVeiculosPorFabricante();

    // Buscar veículos registrados na última semana
    @Query("SELECT v FROM Veiculo v WHERE v.created >= :dataInicio")
    List<Veiculo> findVeiculosRegistradosNaUltimaSemana(LocalDateTime dataInicio);

    @Query("SELECT v FROM Veiculo v WHERE v.vendido = false")
    List<Veiculo> findVeiculosNaoVendidos();
}
