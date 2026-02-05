package com.grupoBL8.transaction_manager.estatisticas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;

@Component

public class EstatisticaProperties {
    @Value("${app.estatistica.segundos}")
    Integer segundos;

    public OffsetDateTime segundosRetorno(){
    // Pega o horario Atual
    OffsetDateTime horarioAtual = OffsetDateTime.now();
    // Pega o horaio atual menos segundos escolhido, caso queira colocar mais tempo, basta aumentar os segundos.
    if(segundos < 0){
       throw new IllegalArgumentException("Erro: Valor de busca por minunto inválido");
    }
    OffsetDateTime horarioDeRetorno = horarioAtual.minusSeconds(segundos);

    return horarioDeRetorno;
    }

}
