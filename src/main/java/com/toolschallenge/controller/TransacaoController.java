package com.toolschallenge.controller;

import com.toolschallenge.dto.TransacaoRequestDTO;
import com.toolschallenge.dto.TransacaoResponseDTO;
import com.toolschallenge.service.ConsultaService;
import com.toolschallenge.service.EstornoService;
import com.toolschallenge.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por expor as APIs REST relacionadas à manipulação de transações.
 *
 * <p>Esta classe centraliza as operações de:</p>
 * <ul>
 *     <li>Pagamento de transações</li>
 *     <li>Estorno de transações</li>
 *     <li>Consulta de transações (por ID ou todas)</li>
 * </ul>
 */
@RestController
public class TransacaoController {

    private final PagamentoService pagamentoService;
    private final EstornoService estornoService;
    private final ConsultaService consultaService;

    @Autowired
    public TransacaoController(PagamentoService pagamentoService,
                               EstornoService estornoService,
                               ConsultaService consultaService) {
        this.pagamentoService = pagamentoService;
        this.estornoService = estornoService;
        this.consultaService = consultaService;
    }

    /**
     * Endpoint responsável por processar uma nova transação de pagamento.
     *
     * @param requestDTO objeto com os dados da transação a ser processada
     * @return {@link ResponseEntity} contendo o resultado da transação
     */
    @PostMapping("/pagamentos")
    public ResponseEntity<TransacaoResponseDTO> realizarPagamento(@RequestBody TransacaoRequestDTO requestDTO) {
        TransacaoResponseDTO response = pagamentoService.realizarPagamento(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint responsável por realizar o estorno (cancelamento) de uma transação.
     *
     * @param id identificador único da transação a ser estornada
     * @return {@link ResponseEntity} contendo os dados da transação após o estorno
     */
    @PostMapping("/estorno/{id}")
    public ResponseEntity<TransacaoResponseDTO> realizarEstorno(@PathVariable String id) {
        TransacaoResponseDTO response = estornoService.realizarEstorno(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint responsável por consultar uma ou mais transações.
     *
     * <ul>
     *     <li>Se o parâmetro <b>id</b> for informado, retorna apenas a transação correspondente.</li>
     *     <li>Se o parâmetro <b>id</b> não for informado, retorna todas as transações.</li>
     * </ul>
     *
     * @param id (opcional) identificador da transação
     * @return {@link ResponseEntity} contendo a transação específica ou a lista completa
     */
    // Api responsável por gerenciar as transações de consulta de todas transaçãos ou por ID
    @GetMapping({"/consulta", "/consulta/{id}"})
    public ResponseEntity<?> consultarTransacoes(@PathVariable(required = false) String id) {

        if (id != null) {
            TransacaoResponseDTO response = consultaService.consultarTransacaoPorId(id);
            return ResponseEntity.ok(response);
        }

        else {
            List<TransacaoResponseDTO> responseList = consultaService.consultarTodasTransacoes();
            return ResponseEntity.ok(responseList);
        }
    }
}