package com.grupoBL8.transaction_manager.estatisticas;

import com.grupoBL8.transaction_manager.transacao.TransacaoModel;
import com.grupoBL8.transaction_manager.transacao.TransacaoRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EstatisticasService {

    private final TransacaoRepository transacaoRepository;
    private final EstatisticaProperties estatisticaProperties;

    public EstatisticasService(TransacaoRepository transacaoRepository, EstatisticaProperties estatisticaProperties){
        this.transacaoRepository = transacaoRepository;
        this.estatisticaProperties = estatisticaProperties;
    }

    public EstatisticasDTO estatistica(){
        EstatisticasDTO estatisticasDTO = new EstatisticasDTO();

        // Listas que salvam os dados da TransacaoModel.
        List<TransacaoModel> listaValoreDataHora = transacaoRepository.listarTodos();
        List<BigDecimal> valoresListaBigDecimal = new ArrayList<>();
        List<TransacaoModel> listaValoresEDataValida = new ArrayList<>();

        // Configurando os Segundos para retonar as estatisticas

        OffsetDateTime horarioAtual = OffsetDateTime.now();
        OffsetDateTime segundosConsulda = estatisticaProperties.segundosRetorno();

        for(TransacaoModel valoresEData : listaValoreDataHora){

            OffsetDateTime dataHora = valoresEData.getDataHora();

            if((dataHora.isBefore(horarioAtual)) && (dataHora.isAfter(segundosConsulda) || dataHora.isEqual(segundosConsulda))) {

                listaValoresEDataValida.add(valoresEData);

                BigDecimal valoresValido = valoresEData.getValor();

                valoresListaBigDecimal.add(valoresValido);
            }
        }

        if(listaValoresEDataValida.size() == 0){

            estatisticasDTO.setCount(0L);
            estatisticasDTO.setSum(0);
            estatisticasDTO.setAvg(0);
            estatisticasDTO.setMin(0);
            estatisticasDTO.setMax(0);

        } else {

            BigDecimal menorValorBigDecimal = Collections.min(valoresListaBigDecimal);
            BigDecimal maiorValorBigDecimal = Collections.max(valoresListaBigDecimal);
            estatisticasDTO.setCount(listaValoresEDataValida.stream().count());

            estatisticasDTO.setSum(valoresListaBigDecimal.stream().reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
            estatisticasDTO.setAvg(estatisticasDTO.getSum() / estatisticasDTO.getCount());
            estatisticasDTO.setMin(menorValorBigDecimal.doubleValue());
            estatisticasDTO.setMax(maiorValorBigDecimal.doubleValue());
        }
        return estatisticasDTO;
    }
}
