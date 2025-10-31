package com.toolschallenge.service.impl;

import com.toolschallenge.dto.TransacaoResponseDTO;
import com.toolschallenge.dto.enums.StatusTransacao;
import com.toolschallenge.model.Transacao;
import com.toolschallenge.repository.TransacaoRepository;
import com.toolschallenge.service.EstornoService;
import com.toolschallenge.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstornoServiceImpl implements EstornoService {

    private final TransacaoRepository repository;
    private final MapperUtils mapperUtils;

    @Override
    @Transactional
    public TransacaoResponseDTO realizarEstorno(String id) {
        Optional<Transacao> transacaoOpt = repository.findById(id);

        if (transacaoOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada para o estorno.");
        }

        Transacao transacao = transacaoOpt.get();

        if (transacao.getStatus() != StatusTransacao.AUTORIZADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O estorno só é permitido para transações com status AUTORIZADO.");
        }

        transacao.setStatus(StatusTransacao.CANCELADO);

        Transacao savedTransacao = repository.save(transacao);
        return mapperUtils.mapToResponseDTO(savedTransacao);
    }
}
