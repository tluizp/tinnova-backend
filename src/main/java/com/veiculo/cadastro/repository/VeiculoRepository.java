package com.veiculo.cadastro.repository;

import com.veiculo.cadastro.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    /**
     * Este método retorna a quantidade de veiculos em relacao a sua decada de fabricacao
     *
     * @return Objeto com a decada e a quantidade de veiculos dessa decada.
     */
    @Query("SELECT (v.ano / 10) * 10 AS decada, COUNT(v) AS quantidade FROM Veiculo v GROUP BY decada ORDER BY decada")
    List<Object[]> findVehiclesByDecade();

    /**
     * Este método retorna a quantidade de veiculos em relacao aos seus fabricantes
     *
     * @return Objeto com a marca e a quantidade de veiculos dessa marca.
     */
    @Query("SELECT v.marca AS marca, COUNT(v) AS quantidade FROM Veiculo v GROUP BY v.marca ORDER BY quantidade DESC")
    List<Object[]> findVehiclesByManufacturer();

    /**
     * Este método retorna veiculos registrados em relacao a data de início
     *
     * @param dataInicio Refere-se a data de inicio
     * @return Veiculos registrados a partir da data de inicio.
     */
    @Query("SELECT v FROM Veiculo v WHERE v.created >= :dataInicio")
    List<Veiculo> findVehiclesRegisteredInRelation(LocalDateTime dataInicio);

    /**
     * Este método retorna os veiculos que ainda nao foram vendidos.
     */
    @Query("SELECT v FROM Veiculo v WHERE v.vendido = false")
    List<Veiculo> findUnsoldVehicles();
}
