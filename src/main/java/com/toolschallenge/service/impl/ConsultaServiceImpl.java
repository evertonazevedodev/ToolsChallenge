package com.toolschallenge.service.impl;

import com.toolschallenge.dto.TransacaoResponseDTO;
import com.toolschallenge.model.Transacao;
import com.toolschallenge.repository.TransacaoRepository;
import com.toolschallenge.service.ConsultaService;
import com.toolschallenge.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do serviço {@link ConsultaService} responsável pelas operações
 * de consulta de transações.
 * <p>
 * Esta classe fornece métodos para:
 * <ul>
 *     <li>Consultar todas as transações registradas</li>
 *     <li>Consultar uma transação específica a partir de seu identificador</li>
 * </ul>
 * </p>
 * <p>
 * Utiliza o {@link TransacaoRepository} para acesso aos dados e o {@link MapperUtils}
 * para conversão entre entidades e DTOs.
 * </p>
 *
 *  * @author Everton
 *  * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ConsultaServiceImpl implements ConsultaService {

    private final TransacaoRepository repository;
    private final MapperUtils mapperUtils;

    @Override
    public List<TransacaoResponseDTO> consultarTodasTransacoes() {
        return repository.findAll().stream()
                .map(mapperUtils::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransacaoResponseDTO consultarTransacaoPorId(String id) {
        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada."));

        return mapperUtils.mapToResponseDTO(transacao);
    }
}
