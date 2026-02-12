package com.grupoBL8.transaction_manager.estatisticas;

import com.grupoBL8.transaction_manager.transacao.TransacaoModel;
import com.grupoBL8.transaction_manager.transacao.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class EstatisticasService {

    private final TransacaoRepository transacaoRepository;
    private final EstatisticaProperties estatisticaProperties;

    public EstatisticasService(TransacaoRepository transacaoRepository, EstatisticaProperties estatisticaProperties){
        this.transacaoRepository = transacaoRepository;
        this.estatisticaProperties = estatisticaProperties;
    }

    public EstatisticasDTO estatistica(){
        log.info("Iniciando calculo de estatisticas");
        EstatisticasDTO estatisticasDTO = new EstatisticasDTO();

        // Configurando os Segundos para retonar as estatisticas
        OffsetDateTime horarioAtual = OffsetDateTime.now();
        OffsetDateTime segundosConsulda = estatisticaProperties.segundosRetorno();

        // Listas que salvam os dados da TransacaoModel.
        List<TransacaoModel> transacaos = transacaoRepository.listarTodos();

        List<BigDecimal> valoresValidos = new ArrayList<>();

        for(TransacaoModel transacao : transacaos){

            OffsetDateTime dataHora = transacao.getDataHora();

            if((dataHora.isBefore(horarioAtual)) && (dataHora.isAfter(segundosConsulda) || dataHora.isEqual(segundosConsulda))) {

                valoresValidos.add(transacao.getValor());
            }
        }

        if(valoresValidos.isEmpty()){
            log.info("Nenhuma transação encontrada no intervalo informado nos ultimos {} segundos", estatisticaProperties.segundos());
            return new EstatisticasDTO(0L,0.0,0.0,0.0,0.0);

        } else {
            log.info("calculando as transacaoes nos ultimos: {}",estatisticaProperties.segundos());

            BigDecimal menorValorBigDecimal = Collections.min(valoresValidos);
            BigDecimal maiorValorBigDecimal = Collections.max(valoresValidos);
            estatisticasDTO.setCount((long) valoresValidos.size());

            estatisticasDTO.setSum(valoresValidos.stream().reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
            estatisticasDTO.setAvg(estatisticasDTO.getSum() / estatisticasDTO.getCount());
            estatisticasDTO.setMin(menorValorBigDecimal.doubleValue());
            estatisticasDTO.setMax(maiorValorBigDecimal.doubleValue());

            log.info("Estatisticas calculada com sucesso | totalTransacao: {}", estatisticasDTO.getCount());
        }
        return estatisticasDTO;
    }
}
