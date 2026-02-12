package com.grupoBL8.transaction_manager.estatisticas;

import com.grupoBL8.transaction_manager.Docs.EstatisticaControllerDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("estatistica")
public class EstatisticasController implements EstatisticaControllerDoc {

    private final EstatisticasService estatisticasService;

    public EstatisticasController(EstatisticasService estatisticasService){
        this.estatisticasService = estatisticasService;
    }

    @GetMapping
    public ResponseEntity estatistica(){
        log.info("GET /estatisticas - requisão recebida");

        try{
            log.info("GET /estatisticas - sucesso");
            return ResponseEntity.ok(estatisticasService.estatistica());
        }
        catch (IllegalArgumentException e){
            log.warn("GET /estatisticas - erro de regra de negócio: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e){
            log.warn("GET/ estatisticas - erro inesperado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }

    }

}
