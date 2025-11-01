package com.toolschallenge.service;

import com.toolschallenge.dto.enums.StatusTransacao;
import com.toolschallenge.model.FormaPagamento;
import com.toolschallenge.model.Transacao;
import com.toolschallenge.repository.TransacaoRepository;
import com.toolschallenge.service.impl.EstornoServiceImpl;
import com.toolschallenge.util.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import static com.toolschallenge.dto.enums.TipoPagamento.AVISTA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstornoServiceImplTest {

    @Mock
    private TransacaoRepository repository;

    @Spy
    private MapperUtils mapperUtils = new MapperUtils();

    @InjectMocks
    private EstornoServiceImpl estornoService;

    private Transacao transacaoAutorizada;
    private Transacao transacaoCancelada;

    @BeforeEach
    void setUp() {

        // Dados Mínimos de Mock para passar pelo MapperUtils
        BigDecimal valorMock = new BigDecimal("50.00");
        LocalDateTime dataHoraMock = LocalDateTime.now();
        FormaPagamento fpMock = new FormaPagamento();
        fpMock.setTipo(AVISTA);
        fpMock.setParcelas(1);

        // Transação em estado válido para estorno (E001)
        transacaoAutorizada = new Transacao();
        transacaoAutorizada.setId("E001");
        transacaoAutorizada.setStatus(StatusTransacao.AUTORIZADO);
        transacaoAutorizada.setCartao("1234567890123456");
        transacaoAutorizada.setValor(valorMock);
        transacaoAutorizada.setDataHora(dataHoraMock);
        transacaoAutorizada.setFormaPagamento(fpMock);


        // Transação em estado inválido para estorno (E002)
        transacaoCancelada = new Transacao();
        transacaoCancelada.setId("E002");
        transacaoCancelada.setStatus(StatusTransacao.CANCELADO);
        transacaoCancelada.setCartao("1234567890123456");
        transacaoCancelada.setValor(valorMock);
        transacaoCancelada.setDataHora(dataHoraMock);
        transacaoCancelada.setFormaPagamento(fpMock);
    }

    // Cenário 1: Sucesso no Estorno
    @Test
    void realizarEstorno_Deve_AlterarStatusParaCancelado_QuandoAutorizado() {

        when(repository.findById("E001")).thenReturn(Optional.of(transacaoAutorizada));
        when(repository.save(any(Transacao.class))).thenReturn(transacaoAutorizada);

        estornoService.realizarEstorno("E001");

        assertEquals(StatusTransacao.CANCELADO, transacaoAutorizada.getStatus());

        verify(repository, times(1)).save(transacaoAutorizada);
        verify(mapperUtils, times(1)).mapToResponseDTO(transacaoAutorizada);
    }

    // Cenário 2: Falha (Transação Não Encontrada)
    @Test
    void realizarEstorno_Deve_LancarExcecao_QuandoNaoEncontrada() {

        when(repository.findById("E003")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                estornoService.realizarEstorno("E003")
        );
        verify(repository, never()).save(any(Transacao.class));
    }

    // Cenário 3: Falha (Status Inválido)
    @Test
    void realizarEstorno_Deve_LancarExcecao_QuandoStatusDiferenteDeAutorizado() {

        when(repository.findById("E002")).thenReturn(Optional.of(transacaoCancelada));

        assertThrows(ResponseStatusException.class, () ->
                estornoService.realizarEstorno("E002")
        );

        verify(repository, never()).save(any(Transacao.class));
    }
}