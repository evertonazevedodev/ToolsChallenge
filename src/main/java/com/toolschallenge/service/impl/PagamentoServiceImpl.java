package com.toolschallenge.service.impl;

import com.toolschallenge.dto.TransacaoRequestDTO;
import com.toolschallenge.dto.TransacaoResponseDTO;
import com.toolschallenge.dto.enums.StatusTransacao;
import com.toolschallenge.model.Transacao;
import com.toolschallenge.repository.TransacaoRepository;
import com.toolschallenge.service.PagamentoService;
import com.toolschallenge.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

//public class PagamentoServiceImpl implements PagamentoService {
//    @Override
//    public TransacaoResponseDTO realizarPagamento(TransacaoRequestDTO requestDTO) {
//        return null;
//    }
//}

@Service
@RequiredArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private final TransacaoRepository repository;
    private final MapperUtils mapperUtils;


    @Override
    @Transactional
    public TransacaoResponseDTO realizarPagamento(TransacaoRequestDTO requestDTO) {
        String transacaoId = requestDTO.getTransacao().getId();

        // 1. Verificar se o ID da transação já existe
        if (repository.existsById(transacaoId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID da transação já existe.");
        }

        // 2. Criar nova transação com status simulado
        Transacao novaTransacao = mapperUtils.mapToEntity(requestDTO);
        novaTransacao.setStatus(simularStatusTransacao());
        novaTransacao.setNsu(gerarNsu());
        novaTransacao.setCodigoAutorizacao(gerarCodigoAutorizacao());

        // 4. Salvar a transação no repositório e retornar resposta
        Transacao savedTransacao = repository.save(novaTransacao);
        return mapperUtils.mapToResponseDTO(savedTransacao);
    }

    private StatusTransacao simularStatusTransacao() {
        return new Random().nextBoolean() ? StatusTransacao.AUTORIZADO : StatusTransacao.NEGADO;
    }

    private String gerarNsu() {
        return String.format("%010d", new Random().nextInt(1000000000));
    }

    private String gerarCodigoAutorizacao() {
        return String.format("%09d", new Random().nextInt(100000000));
    }
}
