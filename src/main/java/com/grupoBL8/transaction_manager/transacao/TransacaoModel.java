package com.grupoBL8.transaction_manager.transacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoModel {
    private BigDecimal valor;
    private OffsetDateTime dataHora;
}