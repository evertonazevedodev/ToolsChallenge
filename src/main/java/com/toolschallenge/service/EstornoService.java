package com.toolschallenge.service;

import com.toolschallenge.dto.TransacaoRequestDTO;
import com.toolschallenge.dto.TransacaoResponseDTO;

public interface EstornoService {
    TransacaoResponseDTO realizarEstorno(String id);
}

