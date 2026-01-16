package com.grupoBL8.transaction_manager.controller;

import com.grupoBL8.transaction_manager.model.TransacaoModel;
import com.grupoBL8.transaction_manager.service.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @GetMapping("/todas")
    public ResponseEntity<List<TransacaoModel>> listarTodas(){
        return ResponseEntity.ok(transacaoService.listarTodos());

    }

    @PostMapping
    public ResponseEntity<TransacaoModel> criar(@RequestBody TransacaoModel transacaoModel){
        return ResponseEntity.ok(transacaoService.criar(transacaoModel));
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<String> estatisticas(){
        return ResponseEntity.ok(transacaoService.estatisticas());
    }

}
