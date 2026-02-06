package com.grupoBL8.transaction_manager.estatisticas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.time.OffsetDateTime;

@ConfigurationProperties(prefix = "estatistica")
public record EstatisticaProperties(Integer segundos) {

    public OffsetDateTime segundosRetorno(){

    // Pega o horario Atual
    OffsetDateTime horarioAtual = OffsetDateTime.now();

    // Pega o horaio atual menos segundos escolhido, caso queira colocar mais tempo, basta aumentar os segundos.
    if(segundos < 0){
       throw new IllegalArgumentException("Erro: Valor de busca por minunto inválido");
    }

    // Ocorre a subtracao da hora.
    OffsetDateTime horarioDeRetorno = horarioAtual.minusSeconds(segundos);

    return horarioDeRetorno;
    }

}
