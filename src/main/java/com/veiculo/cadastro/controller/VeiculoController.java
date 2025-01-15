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
    public List<Veiculo> listarVeiculos(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String cor
    ) {
        return veiculoService.listar(marca, ano, cor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarVeiculo(@PathVariable Long id) {
        return veiculoService.buscarPor(id);
    }

    @PostMapping
    public ResponseEntity<?> salvarVeiculo(@RequestBody Veiculo veiculo) {
        return veiculoService.salvar(veiculo);
    }

    @PutMapping("/{id}")
    public Optional<Veiculo> atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculoAtualizado) {
        return veiculoService.atualizar(id, veiculoAtualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Veiculo> atualizarParcialVeiculo(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        return veiculoService.atualizarParcial(id, campos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVeiculo(@PathVariable Long id) {
        return veiculoService.deletar(id);
    }

    @GetMapping("/estatisticas")
    public Map<String, Object> obterEstatisticas() {
        return veiculoService.obterEstatisticas();
    }

}
