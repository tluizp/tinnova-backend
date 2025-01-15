package com.veiculo.cadastro.interfaces;

import com.veiculo.cadastro.model.Veiculo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IVeiculoService {
    List<Veiculo> list(String marca, Integer ano, String cor);
    ResponseEntity<?> findBy(Long id);
    ResponseEntity<?> save(Veiculo veiculo);
    Optional<Veiculo> update(Long id, Veiculo veiculoAtualizado);
    ResponseEntity<Void> delete(Long id);
    Map<String, Object> getStatistics();
    ResponseEntity<Veiculo> updatePartial(Long id, Map<String, Object> campos);
}
