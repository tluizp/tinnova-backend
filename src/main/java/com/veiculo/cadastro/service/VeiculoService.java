package com.veiculo.cadastro.service;

import com.veiculo.cadastro.dto.DecadaDTO;
import com.veiculo.cadastro.dto.FabricanteDTO;
import com.veiculo.cadastro.interfaces.IVeiculoService;
import com.veiculo.cadastro.model.Marcas;
import com.veiculo.cadastro.model.Veiculo;
import com.veiculo.cadastro.repository.VeiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VeiculoService implements IVeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    public List<Veiculo> list(String marca, Integer ano, String cor) {
        if (marca != null || ano != null || cor != null) {
            return veiculoRepository.findAll().stream()
                    .filter(veiculo -> (marca == null || veiculo.getMarca().equalsIgnoreCase(marca)) &&
                            (ano == null || veiculo.getAno().equals(ano)) &&
                            (cor == null || veiculo.getCor().contains(cor)))
                    .collect(Collectors.toList());
        }
        return veiculoRepository.findAll();
    }

    @Override
    public ResponseEntity<?> findBy(Long id) {
        Optional<Veiculo> veiculo = veiculoRepository.findById(id);
        if (veiculo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Elemento nao encontrado, com id: " + id);
        }
        return ResponseEntity.ok(veiculo);
    }

    @Override
    public ResponseEntity<?> save(Veiculo veiculo) {
        boolean present = false;
        if (Objects.nonNull(veiculo.getMarca())){
            present = Marcas.verificaMarca(veiculo.getMarca()).isPresent();
            if (!present) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body("Marca nao encontrada: " + veiculo.getMarca());
            }
        }
        if (veiculo.getId() == null && present) {
            veiculo.setCreated(LocalDateTime.now());
            veiculo.setUpdated(LocalDateTime.now());
            veiculoRepository.save(veiculo);
        }
        return ResponseEntity.ok(veiculo);
    }

    @Override
    public Optional<Veiculo> update(Long id, Veiculo veiculoAtualizado) {
        return veiculoRepository.findById(id).map(veiculo -> {
            veiculo.setVeiculo(veiculoAtualizado.getVeiculo());
            veiculo.setMarca(veiculoAtualizado.getMarca());
            veiculo.setAno(veiculoAtualizado.getAno());
            veiculo.setDescricao(veiculoAtualizado.getDescricao());
            veiculo.setVendido(veiculoAtualizado.getVendido());
            veiculo.setUpdated(LocalDateTime.now());
            return veiculoRepository.save(veiculo);
        });
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        if (veiculoRepository.existsById(id)) {
            veiculoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        statistics.put("decadas", this.obterVeiculosPorDecada());
        statistics.put("fabricantes", this.obterVeiculosPorFabricante());
        statistics.put("ultimos7Dias", this.obterVeiculosRegistradosNaUltimaSemana());
        statistics.put("naoVendidos", this.obterVeiculosNaoVendidos());

        return statistics;
    }

    @Override
    public ResponseEntity<Veiculo> updatePartial(Long id, Map<String, Object> campos) {
        return veiculoRepository.findById(id).map(veiculo -> {
            campos.forEach((campo, valor) -> {
                Field field = ReflectionUtils.findField(Veiculo.class, campo);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, veiculo, valor);
                }
            });
            veiculo.setUpdated(LocalDateTime.now());
            veiculoRepository.save(veiculo);
            return ResponseEntity.ok(veiculo);
        }).orElse(ResponseEntity.notFound().build());
    }

    private List<DecadaDTO> obterVeiculosPorDecada() {
        return veiculoRepository.findVehiclesByDecade()
                .stream()
                .map(obj -> new DecadaDTO((int) obj[0], (long) obj[1]))
                .collect(Collectors.toList());
    }

    private List<FabricanteDTO> obterVeiculosPorFabricante() {
        return veiculoRepository.findVehiclesByManufacturer()
                .stream()
                .map(obj -> new FabricanteDTO((String) obj[0], (long) obj[1]))
                .collect(Collectors.toList());
    }

    private List<Veiculo> obterVeiculosRegistradosNaUltimaSemana() {
        LocalDateTime seteDiasAtras = LocalDateTime.now().minusDays(7);
        return veiculoRepository.findVehiclesRegisteredInRelation(seteDiasAtras);
    }

    private List<Veiculo> obterVeiculosNaoVendidos() {
        return veiculoRepository.findUnsoldVehicles();
    }

}
