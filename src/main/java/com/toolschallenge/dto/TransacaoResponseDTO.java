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

        private String cartao;
        private String id;
        private Descricao descricao;
        private FormaPagamento formaPagamento;

        /**
         * MÁSCARA DO CARTÃO: Método getter customizado que será chamado
         * pelo Jackson na hora de serializar o JSON.
         * Garante que o número retornado siga o formato: 4444********9010
         */
        public String getCartao() {
            if (this.cartao == null || this.cartao.length() < 8) {
                return this.cartao; // Retorna sem máscara se for muito curto
            }

            // 1. Remove espaços do número do cartão.
            String cartaoLimpo = this.cartao.replaceAll("\\s", "");

            // 2. Extrai os primeiros 4 e os últimos 4 dígitos.
            String prefixo = cartaoLimpo.substring(0, 4);
            String sufixo = cartaoLimpo.substring(cartaoLimpo.length() - 4);

            // 3. Calcula o número de asteriscos.
            int asteriscos = cartaoLimpo.length() - 8;

            // 4. Constrói o cartão mascarado.
            return prefixo + "*".repeat(asteriscos) + sufixo;
        }
    }

    @Data
    public static class Descricao {
        private String valor;
        private String dataHora;
        private String estabelecimento;

        // Campos adicionais presentes na resposta
        private String nsu;
        private String codigoAutorizacao;
        private StatusTransacao status;
    }

    @Data
    public static class FormaPagamento {
        private String tipo;
        private String parcelas;
    }

}