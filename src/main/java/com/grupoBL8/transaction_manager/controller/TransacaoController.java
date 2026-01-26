package com.grupoBL8.transaction_manager.controller;

import com.grupoBL8.transaction_manager.EstatisticasDTO;
import com.grupoBL8.transaction_manager.TransacaoDTO;
import com.grupoBL8.transaction_manager.model.TransacaoModel;
import com.grupoBL8.transaction_manager.repository.TransacaoRepository;
import com.grupoBL8.transaction_manager.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacaoService transacaoService, TransacaoRepository transacaoRepository){
        this.transacaoService = transacaoService;
        this.transacaoRepository = transacaoRepository;
    }

    // Lista todas as transacoes
    @GetMapping
    public ResponseEntity<List<TransacaoModel>> listarTodas(){
        return ResponseEntity.ok(transacaoRepository.listarTodos());
    }

    // Listar por Posicao
    @GetMapping("/{posicao}")
    public ResponseEntity listarPosicao(@PathVariable int posicao){
       try {
           return ResponseEntity.ok(transacaoService.listaPosicao(posicao));
       }
       catch (IllegalArgumentException e){
           return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
       }
    }

    // Criar uma nova transacao
    @PostMapping
    public ResponseEntity criar(@RequestBody TransacaoDTO transacaoDTO) {
        try {
            transacaoService.criar(transacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (IllegalArgumentException  e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
        catch (HttpClientErrorException.BadRequest e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Atualizar
    @PutMapping("/{posicao}")
    public ResponseEntity<TransacaoDTO> atualizar(@PathVariable int posicao, @RequestBody TransacaoDTO transacaoDTO){
        return ResponseEntity.ok(transacaoService.atualizar(posicao, transacaoDTO));
    }

    // Deletar
    @DeleteMapping
    public ResponseEntity<Void> apagarTodasTransacoes(){
        transacaoRepository.limparDados();
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

    // Estatisticas
    @GetMapping("/estatisticas")
    public ResponseEntity<EstatisticasDTO> count(){
        return ResponseEntity.ok(transacaoService.estatisticas());
    }

}
