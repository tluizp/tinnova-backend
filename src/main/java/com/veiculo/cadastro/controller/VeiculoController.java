package com.veiculo.cadastro.controller;

import com.veiculo.cadastro.model.Veiculo;
import com.veiculo.cadastro.service.VeiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public List<Veiculo> list(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String cor
    ) {
        return veiculoService.list(marca, ano, cor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return veiculoService.findBy(id);
    }

    @PostMapping
    public ResponseEntity<?> Save(@RequestBody Veiculo veiculo) {
        return veiculoService.save(veiculo);
    }

    @PutMapping("/{id}")
    public Optional<Veiculo> update(@PathVariable Long id, @RequestBody Veiculo veiculoAtualizado) {
        return veiculoService.update(id, veiculoAtualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Veiculo> updatePartial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        return veiculoService.updatePartial(id, campos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return veiculoService.delete(id);
    }

    @GetMapping("/estatisticas")
    public Map<String, Object> getStatistics() {
        return veiculoService.getStatistics();
    }

}
