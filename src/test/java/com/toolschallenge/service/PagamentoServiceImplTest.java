package com.toolschallenge.service;

import com.toolschallenge.dto.TransacaoRequestDTO;
import com.toolschallenge.dto.enums.TipoPagamento;
import com.toolschallenge.model.Transacao;
import com.toolschallenge.repository.TransacaoRepository;
import com.toolschallenge.service.impl.PagamentoServiceImpl;
import com.toolschallenge.util.MapperUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceImplTest {


    @Mock
    private TransacaoRepository repository;

    @Spy
    private MapperUtils mapperUtils = new MapperUtils();

    @InjectMocks
    private PagamentoServiceImpl pagamentoService;
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
    }

    // Cenário 1: Sucesso na Criação
    @Test
    void realizarPagamento_Deve_SalvarNovaTransacao_QuandoIdNaoExiste() {

        when(repository.existsById("P001")).thenReturn(false);
        when(repository.save(any(Transacao.class))).thenAnswer(invocation -> {
            return invocation.<Transacao>getArgument(0); // Retorna a entidade já mapeada e completa
        });

        pagamentoService.realizarPagamento(mockRequest);

        verify(repository, times(1)).existsById("P001");
        verify(repository, times(1)).save(any(Transacao.class));
    }

    // Cenário 2: Falha (ID Duplicado)
    @Test
    void realizarPagamento_Deve_LancarExcecao_QuandoIdJaExiste() {

        when(repository.existsById("P001")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () ->
                pagamentoService.realizarPagamento(mockRequest)
        );

        verify(repository, never()).save(any(Transacao.class));
    }
}