package com.toolschallenge.service;

import com.toolschallenge.dto.TransacaoRequestDTO;
import com.toolschallenge.dto.TransacaoResponseDTO;
import com.toolschallenge.dto.enums.TipoPagamento;
import com.toolschallenge.model.Transacao;
import com.toolschallenge.repository.TransacaoRepository;
import com.toolschallenge.service.impl.ConsultaServiceImpl;
import com.toolschallenge.util.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultaServiceImplTest {

    @Mock
    private TransacaoRepository repository;

    @Spy
    private MapperUtils mapperUtils = new MapperUtils();

    @InjectMocks
    private ConsultaServiceImpl consultaService;
    private Transacao mockEntity;
    private TransacaoRequestDTO mockRequest;

    @BeforeEach
    void setUp() {

        // Configuração de um JSON de entrada válido para reuso em todos os testes
        mockRequest = new TransacaoRequestDTO();
        TransacaoRequestDTO.TransacaoData transacaoData = new TransacaoRequestDTO.TransacaoData();
        transacaoData.setId("P001");
        transacaoData.setCartao("4444 1234 5678 9010");

        TransacaoRequestDTO.Descricao descricao = new TransacaoRequestDTO.Descricao();
        descricao.setValor("100.00");
        descricao.setDataHora("01/05/2024 10:00:00");
        descricao.setEstabelecimento("Loja Teste Pagamento");

        TransacaoRequestDTO.FormaPagamento fp = new TransacaoRequestDTO.FormaPagamento();
        fp.setTipo(TipoPagamento.AVISTA);
        fp.setParcelas("1");

        transacaoData.setDescricao(descricao);
        transacaoData.setFormaPagamento(fp);
        mockRequest.setTransacao(transacaoData);


        mockEntity = mapperUtils.mapToEntity(mockRequest);
    }

    // Cenário 1: Consulta por ID (Sucesso)
    @Test
    void consultarPorId_Deve_RetornarTransacao_QuandoEncontrada() {

        when(repository.findById("P001")).thenReturn(Optional.of(mockEntity));

        doReturn(new TransacaoResponseDTO())
                .when(mapperUtils).mapToResponseDTO(any(Transacao.class));

        consultaService.consultarTransacaoPorId("P001");

        verify(repository, times(1)).findById("P001");
        verify(mapperUtils, times(1)).mapToResponseDTO(mockEntity);
    }

    // Cenário 2: Consulta por ID (Falha)
    @Test
    void consultarPorId_Deve_LancarExcecao_QuandoNaoEncontrada() {

        when(repository.findById("C002")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                consultaService.consultarTransacaoPorId("C002")
        );
    }

    // Cenário 3: Consultar Todas as Transações
    @Test
    void consultarTodas_Deve_RetornarListaDeTransacoes() {

        List<Transacao> transacoesMock = List.of(mockEntity, mockEntity);

        when(repository.findAll()).thenReturn(transacoesMock);

        doReturn(new TransacaoResponseDTO())
                .when(mapperUtils).mapToResponseDTO(any(Transacao.class));

        var resultado = consultaService.consultarTodasTransacoes();

        assertEquals(2, resultado.size());

        verify(repository, times(1)).findAll();
        verify(mapperUtils, times(2)).mapToResponseDTO(any(Transacao.class));
    }
}