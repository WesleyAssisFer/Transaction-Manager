package com.grupoBL8.transaction_manager.Docs;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Estatisticas",
description = "Endpoint reposavel pelo retorno das estatisticas nos ultimos minutos seleciodos")
public interface EstatisticaControllerDoc {

    @Operation(summary = "Retorna as estatisticas",
    description = "As estatisticas é formada por, count(Numero de transao), sum(Soma dos valores das transacao)," +
                  "avg(Media entre valor e transacao), min(Mnor valor da transacao), max(Maior valor da transacao)" +
                  "esse retorno é feito com base nos ultimos 60 segundos das transacao, podendo escolher um maior" +
                  "valor para retorar as estatisticas."
    )

    @ApiResponse(responseCode = "200",
        description = "Retorno das estatisticas realizado com secusso"
    )
    @ApiResponse(responseCode = "422",
    description = "Erro de validacao"
    )
    @ApiResponse(responseCode = "400",
    description = "Erro inesperado"
    )

        ResponseEntity<Void> estatistica();
}
