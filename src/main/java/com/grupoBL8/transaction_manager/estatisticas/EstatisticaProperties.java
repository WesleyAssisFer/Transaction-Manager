package com.grupoBL8.transaction_manager.estatisticas;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
@Validated
@ConfigurationProperties(prefix = "estatistica")
public record EstatisticaProperties(
        @NotNull(message = "Valor da mensagm vazio")
        @Positive(message = "Valor tem que maior que zero")
        Integer segundos) {

    public OffsetDateTime segundosRetorno(){

    // Pega o horario Atual
    OffsetDateTime horarioAtual = OffsetDateTime.now();

    // Ocorre a subtracao da hora.
    OffsetDateTime horarioRetornoEstatistcas = horarioAtual.minusSeconds(segundos);

    return horarioRetornoEstatistcas;
    }
}
