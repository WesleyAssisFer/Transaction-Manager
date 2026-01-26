package com.grupoBL8.transaction_manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDTO {

        private BigDecimal valor;
        private OffsetDateTime dataHora;
    }
