package com.toolschallenge.service;

import com.toolschallenge.dto.TransacaoResponseDTO;

import java.util.List;

public interface ConsultaService {

    TransacaoResponseDTO consultarTransacaoPorId(String id);

    List<TransacaoResponseDTO> consultarTodasTransacoes();
}
