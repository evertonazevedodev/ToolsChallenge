package com.toolschallenge.service.impl;

import com.toolschallenge.dto.TransacaoResponseDTO;
import com.toolschallenge.dto.enums.StatusTransacao;
import com.toolschallenge.model.Transacao;
import com.toolschallenge.repository.TransacaoRepository;
import com.toolschallenge.service.EstornoService;
import com.toolschallenge.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Implementação do serviço responsável pelo processo de estorno de transações.
 * <p>
 * Esta classe contém a regra de negócio para cancelamento (estorno) de uma transação previamente
 * autorizada.
 * </p>
 *
 * @author Everton
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
public class EstornoServiceImpl implements EstornoService {

    private final TransacaoRepository repository;
    private final MapperUtils mapperUtils;

    @Override
    @Transactional
    public TransacaoResponseDTO realizarEstorno(String id) {
        Optional<Transacao> transacaoOpt = repository.findById(id);

        // 1. Verificar se a transação existe
        if (transacaoOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada para o estorno.");
        }

        Transacao transacao = transacaoOpt.get();

        // 2. Verificar se o status da transação permite estorno
        if (transacao.getStatus() != StatusTransacao.AUTORIZADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O estorno só é permitido para transações com status AUTORIZADO.");
        }

        // 3. Relizar o estorno alterando o status da transação
        transacao.setStatus(StatusTransacao.CANCELADO);

        // 4. Salvar a transação atualizada e retornar a resposta
        Transacao savedTransacao = repository.save(transacao);
        return mapperUtils.mapToResponseDTO(savedTransacao);
    }
}
