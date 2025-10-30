package com.toolschallenge.dto;

import com.toolschallenge.dto.enums.StatusTransacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransacaoResponseDTO {

    @JsonProperty("transacao")
    private TransacaoData transacao;

    @Data
    public static class TransacaoData {
        private String id;
        private String cartao;
        private Descricao descricao;
        private FormaPagamento formaPagamento;
    }

    @Data
    public static class Descricao {
        private String valor;
        private String dataHora;
        private String estabelecimento;

        // Campos adicionais presentes na resposta
        private StatusTransacao status;
        private String nsu;
        private String codigoAutorizacao;
    }

    @Data
    public static class FormaPagamento {
        private String tipo;
        private String parcelas;
    }
}