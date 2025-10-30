package com.toolschallenge.dto;

import com.toolschallenge.dto.enums.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransacaoRequestDTO {

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
    }

    @Data
    public static class FormaPagamento {
        private TipoPagamento tipo;
        private String parcelas;
    }
}