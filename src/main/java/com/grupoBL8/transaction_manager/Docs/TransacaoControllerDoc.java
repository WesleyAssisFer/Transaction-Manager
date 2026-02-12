package com.grupoBL8.transaction_manager.Docs;

import com.grupoBL8.transaction_manager.transacao.TransacaoDTO;
import com.grupoBL8.transaction_manager.transacao.TransacaoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Tag(name = "Transacao",
description = "Endpoint resposavel por criar e adicionar uma transacao")
public interface TransacaoControllerDoc{
    // Criar uma transacao
    @Operation(summary = "Cria uma Transacao",
            description = "Caso a transacao seja valida, ela é adicionada na lista"
    )
    @ApiResponse(responseCode = "200",
            description = "Transacao adicionado a lista com sucesso"
    )
    @ApiResponse(responseCode = "422",
            description = "Erro de validacao"
    )
    @ApiResponse(responseCode = "400",
            description = "Erro inesperado"
    )
        ResponseEntity<Void> criar(@RequestBody TransacaoDTO transacaoDTO);

    // Listar todas as transacao
    @Operation(summary = "Lista todas as transacao",
    description = "Mostra a lista de todas as transacao que foram criadas com sucesso"
    )
    @ApiResponse(responseCode = "200",
    description = "Lista retornada com sucesso"
    )
        ResponseEntity<List<TransacaoModel>> listarTodas();

    //Posicao da lista
    @Operation(summary = "Retorna uma transacao especifica",
    description = "Como base na posicao escolhida da lista de transacao, essa posicao será exibida"
    )
    @ApiResponse(responseCode = "200",
    description = "Posicao da lista de transacao retornada com sucesso")
    @ApiResponse(responseCode = "422",
    description = "Erro de validacao, posicao invalida")
        ResponseEntity<Void> listarPosicao(@PathVariable int posicao);

    // Deletar todas as transacao
    @Operation(summary = "Apaga todas as transacao",
    description = "Deleta toda as transacao que estão presente na lista")
    @ApiResponse(responseCode = "200",
    description = "Todos os valores da lista foram apagados com sucesso")

        ResponseEntity<Void> apagarTodasTransacoes();


}