package com.toolschallenge.util;

import com.toolschallenge.dto.TransacaoRequestDTO;
import com.toolschallenge.dto.TransacaoResponseDTO;
import com.toolschallenge.model.Transacao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MapperUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Transacao mapToEntity(TransacaoRequestDTO dto) {

        // 3. Converter os dados da requisição para Entidade
        Transacao transacao = new Transacao();
        transacao.setCartao(dto.getTransacao().getCartao());
        transacao.setId(dto.getTransacao().getId());

        transacao.setValor(new BigDecimal(dto.getTransacao().getDescricao().getValor()));
        transacao.setDataHora(LocalDateTime.parse(dto.getTransacao().getDescricao().getDataHora(), FORMATTER));
        transacao.setEstabelecimento(dto.getTransacao().getDescricao().getEstabelecimento());

        transacao.getFormaPagamento().setTipo(dto.getTransacao().getFormaPagamento().getTipo());
        transacao.getFormaPagamento().setParcelas(Integer.parseInt(dto.getTransacao().getFormaPagamento().getParcelas()));

        return transacao;
    }

    public TransacaoResponseDTO mapToResponseDTO(Transacao entity) {

        // 3. Converter Entidade para DTO contendo os dados da resposta
        TransacaoResponseDTO dto = new TransacaoResponseDTO();
        TransacaoResponseDTO.TransacaoData data = new TransacaoResponseDTO.TransacaoData();

        // 3.1 Mapear dados gerais da Transação
        data.setCartao(entity.getCartao());
        data.setId(entity.getId());

        // 3.2 Mapear dados da Descrição
        TransacaoResponseDTO.Descricao descricao = new TransacaoResponseDTO.Descricao();
        descricao.setValor(entity.getValor().toPlainString());
        descricao.setDataHora(entity.getDataHora().format(FORMATTER));
        descricao.setEstabelecimento(entity.getEstabelecimento());
        descricao.setNsu(entity.getNsu());
        descricao.setCodigoAutorizacao(entity.getCodigoAutorizacao());
        descricao.setStatus(entity.getStatus());
        data.setDescricao(descricao);

        // 3.3 Mapeando dados da Forma de Pagamento
        TransacaoResponseDTO.FormaPagamento formaPagamento = new TransacaoResponseDTO.FormaPagamento();
        formaPagamento.setTipo(entity.getFormaPagamento().getTipo().name());
        formaPagamento.setParcelas(String.valueOf(entity.getFormaPagamento().getParcelas()));
        data.setFormaPagamento(formaPagamento);

        dto.setTransacao(data);
        return dto;
    }
}