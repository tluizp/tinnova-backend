package com.veiculo.cadastro;

import com.veiculo.cadastro.model.Veiculo;
import com.veiculo.cadastro.repository.VeiculoRepository;
import com.veiculo.cadastro.service.VeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listar_comFiltrosDeveRetornarFiltrados() {
        Veiculo veiculo = new Veiculo(1L, "Fusca", "Volkswagen", "Azul",1980,
                "Rodas originais", Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());

        when(veiculoRepository.findAll()).thenReturn(List.of(veiculo));

        List<Veiculo> resultado = veiculoService.list("Volkswagen", 1980, "Azul");

        assertEquals(1, resultado.size());
        assertEquals("Fusca", resultado.get(0).getVeiculo());
        verify(veiculoRepository, times(1)).findAll();
    }

    @Test
    void buscarPor_comIdInexistenteDeveRetornarNotFound() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> resposta = veiculoService.findBy(1L);

        assertEquals(404, resposta.getStatusCodeValue());
        verify(veiculoRepository, times(1)).findById(1L);
    }

    @Test
    void salvar_comMarcaInvalidaDeveRetornarErro() {
        Veiculo veiculo = new Veiculo(null, "Modelo", "MarcaInvalida", "Prata", 2021,
                "Completo", Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());

        ResponseEntity<?> resposta = veiculoService.save(veiculo);

        assertEquals(406, resposta.getStatusCodeValue());
        assertTrue(resposta.getBody().toString().contains("Marca nao encontrada"));
    }

    @Test
    void atualizar_comDadosValidosDeveSalvar() {
        Veiculo veiculo = new Veiculo(null, "GT-R", "Nissan", "Black", 2014,
                "3.8 V6 Bi-turbo", Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());

        ResponseEntity<?> resultado = veiculoService.save(veiculo);

        assertTrue(resultado.hasBody());
        assertEquals(200, resultado.getStatusCodeValue());
        verify(veiculoRepository, times(1)).save(veiculo);
    }

    @Test
    void atualizar_comDadosValidosDeveAtualizar() {
        Veiculo veiculoExistente = new Veiculo(1L, "Fusca", "Volkswagen", "Prata", 1975,
                "Completo", Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());
        Veiculo veiculoAtualizado = new Veiculo(null, "Golf", "Volkswagen", "Prata", 2023,
                "2.0 TSI Turbo", Boolean.FALSE, LocalDateTime.now(), LocalDateTime.now());

        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculoExistente));
        when(veiculoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Veiculo> resultado = veiculoService.update(1L, veiculoAtualizado);

        assertTrue(resultado.isPresent());
        assertEquals("Golf", resultado.get().getVeiculo());
        verify(veiculoRepository, times(1)).save(veiculoExistente);
    }

    @Test
    void deletar_comIdExistenteDeveExcluir() {
        when(veiculoRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> resposta = veiculoService.delete(1L);

        assertEquals(204, resposta.getStatusCodeValue());
        verify(veiculoRepository, times(1)).deleteById(1L);
    }

    @Test
    void obterEstatisticas_deveRetornarDados() {
        when(veiculoRepository.findVehiclesByDecade()).thenReturn(List.<Object[]>of(new Object[]{1980, 10L}));
        when(veiculoRepository.findVehiclesByManufacturer()).thenReturn(List.<Object[]>of(new Object[]{"Volkswagen", 5L}));
        when(veiculoRepository.findVehiclesRegisteredInRelation(any())).thenReturn(List.of());
        when(veiculoRepository.findUnsoldVehicles()).thenReturn(List.of());

        Map<String, Object> estatisticas = veiculoService.getStatistics();

        assertNotNull(estatisticas.get("decadas"));
        assertNotNull(estatisticas.get("fabricantes"));
        verify(veiculoRepository, times(1)).findVehiclesByDecade();
    }
}

