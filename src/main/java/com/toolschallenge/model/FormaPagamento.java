package com.toolschallenge.model;

import com.toolschallenge.dto.enums.TipoPagamento;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable //Faz parte da entidade Transacao
public class FormaPagamento {

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipo;
    private Integer parcelas;

    // Construtor padrão
    public FormaPagamento() {
        this.parcelas = 1; // Default
    }
}