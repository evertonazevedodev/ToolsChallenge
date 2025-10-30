package com.toolschallenge.model;

import com.toolschallenge.dto.enums.StatusTransacao;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // ID único da transação
    private String cartao;

    // Detalhes da Descrição
    private BigDecimal valor;
    private LocalDateTime dataHora;
    private String estabelecimento;

    // Informações adicionais do sistema de pagamento
    private String nsu;
    private String codigoAutorizacao;

    // Status da Transação
    @Enumerated(EnumType.STRING)
    private StatusTransacao status;

    // Integrando as colunas sobre forma do Pagamento na entidade Transacao
    @Embedded
    private FormaPagamento formaPagamento = new FormaPagamento();
}