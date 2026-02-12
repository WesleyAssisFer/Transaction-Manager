package com.grupoBL8.transaction_manager.transacao;

import com.grupoBL8.transaction_manager.Docs.TransacaoControllerDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transacao")
public class TransacaoController implements TransacaoControllerDoc{

    private final TransacaoService transacaoService;
    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacaoService transacaoService, TransacaoRepository transacaoRepository){
        this.transacaoService = transacaoService;
        this.transacaoRepository = transacaoRepository;
    }

    // Criar uma nova transacao
    @PostMapping
    public ResponseEntity criar(@RequestBody TransacaoDTO transacaoDTO) {
        log.info("POST /transacao - requisicao recebida");
        try {
            transacaoService.criar(transacaoDTO);
            log.info("POST /transacao - transacao criado com sucesso");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (IllegalArgumentException  e) {
            log.warn("POST /transacao - erro de validacao: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
        catch (HttpClientErrorException.BadRequest e){
            log.warn("POST /transacao - requisicao invalida: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(Exception e){
            log.error("POST /transacao - erro inesperado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }

    // Lista todas as transacoes
    @GetMapping
    public ResponseEntity<List<TransacaoModel>> listarTodas(){
        log.info("GET /transacao - listar todas realizida com sucesso");
        return ResponseEntity.ok(transacaoRepository.listarTodos());
    }

    // Listar por Posicao
    @GetMapping("/{posicao}")
    public ResponseEntity listarPosicao(@PathVariable int posicao){
       try {
           log.info("GET /transacao/{}",posicao, "posicao visivel");
           return ResponseEntity.ok(transacaoService.listaPosicao(posicao));
       }
       catch (IllegalArgumentException e){
           log.warn("GET /transacao/posicao - Erro ao tentar acessar posicao");
           return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
       }
    }


    // Deletar
    @DeleteMapping
    public ResponseEntity apagarTodasTransacoes(){
        transacaoRepository.limparDados();
        log.info("DELET /transacao - transacoes deletada com sucesso");
        return ResponseEntity.status(HttpStatus.valueOf(200)).build();
    }

}
