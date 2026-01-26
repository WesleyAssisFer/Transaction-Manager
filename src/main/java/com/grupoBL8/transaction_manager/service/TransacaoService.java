package com.grupoBL8.transaction_manager.service;

import com.grupoBL8.transaction_manager.EstatisticasDTO;
import com.grupoBL8.transaction_manager.TransacaoDTO;
import com.grupoBL8.transaction_manager.TransacaoMapper;
import com.grupoBL8.transaction_manager.model.TransacaoModel;
import com.grupoBL8.transaction_manager.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final TransacaoMapper transacaoMapper;

    public TransacaoService (TransacaoRepository transacaoRepository, TransacaoMapper transacaoMapper){
        this.transacaoRepository = transacaoRepository;
        this.transacaoMapper = transacaoMapper;
    }

    //Criar
    public TransacaoDTO criar(TransacaoDTO transacaoDTO){
        if(transacaoDTO.getValor().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Erro: Valor negativo");
        }
        if(transacaoDTO.getDataHora().isAfter(OffsetDateTime.now())){
            throw new IllegalArgumentException("Erro: Data ou horario não compativeis");
        }
        if(transacaoDTO.getValor().equals(null)){
            throw new IllegalArgumentException();
        }
        if (transacaoDTO.getDataHora().equals(null)){
            throw new IllegalArgumentException();
        }
        TransacaoModel model = transacaoMapper.map(transacaoDTO);
        transacaoRepository.salvarDados(model);
        TransacaoDTO dadosDTO = transacaoMapper.map(model);
        return dadosDTO;
    }

    // Atuaizar
    public TransacaoDTO atualizar(int posicao, TransacaoDTO novaTrasacaoDTO){
        if(novaTrasacaoDTO.getValor().compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Erro: Valor negativo não é permitodo");
        }
        if(novaTrasacaoDTO.getDataHora().isAfter(OffsetDateTime.now())){
            throw new IllegalArgumentException("Erro: essa data ou hora são invalidas");
        }

        if(posicao >= 0 && posicao < transacaoRepository.listarTodos().size()){
           transacaoRepository.listarTodos().get(posicao).setValor(novaTrasacaoDTO.getValor());
           transacaoRepository.listarTodos().get(posicao).setDataHora(novaTrasacaoDTO.getDataHora());

            TransacaoModel valorModelAtualizado = transacaoRepository.listarTodos().get(posicao);

            TransacaoDTO dto = transacaoMapper.map(valorModelAtualizado);
           return dto;
        } else {
            throw new IllegalArgumentException("Transacao nao encontrada");
        }
    }

    // Listar por Posicao
    public TransacaoDTO listaPosicao(int posicao){
      if(posicao > transacaoRepository.listarTodos().size() || posicao < 0){
          throw new IllegalArgumentException("Posicao não encotrada");
      }
      return transacaoMapper.map(transacaoRepository.listarTodos().get(posicao));
    }


    // Estatisticas
    public EstatisticasDTO estatisticas(){
        // Cria um objeto
        EstatisticasDTO estatisticasDTO = new EstatisticasDTO();
        // Pega o horario Atual
        OffsetDateTime horarioAtual = OffsetDateTime.now();
        // Pega o horaio atual - 1 min
        OffsetDateTime umMinutoAtras = horarioAtual.minusMinutes(1);

        // tem que pegar a lista como um todo uma transferencia completa
        List<OffsetDateTime> dataHoraLista= transacaoRepository.listarTodos().stream().map(TransacaoModel::getDataHora).toList();
        for(OffsetDateTime dataHoraAno : dataHoraLista){
            if(dataHoraAno.getYear() == OffsetDateTime.now().getYear()){
                
            }
        }

        // Pega somente os horario presente nas validacoes
        List<OffsetDateTime> listasDosHorariosValidos = transacaoRepository.listarTodos().stream().map(TransacaoModel::getDataHora).toList();

        for(OffsetDateTime dataHora : listasDosHorariosValidos){

            if((dataHora.isBefore(horarioAtual)) && (dataHora.isAfter(umMinutoAtras) || dataHora.isEqual(umMinutoAtras)) && dataHora.getYear() == OffsetDateTime.now().getYear()){
                List<TransacaoModel> listaTemp = new ArrayList<>(transacaoRepository.listarTodos());

                List<BigDecimal> valoresListaBigDecimal = listaTemp.stream().map(TransacaoModel::getValor).toList();
                BigDecimal menorValorBigDecimal = Collections.min(valoresListaBigDecimal);
                BigDecimal maiorValorBigDecimal = Collections.max(valoresListaBigDecimal);

                estatisticasDTO.setCount(listaTemp.size());
                estatisticasDTO.setSum(valoresListaBigDecimal.stream().reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
                estatisticasDTO.setAvg(estatisticasDTO.getSum()/estatisticasDTO.getCount());
                estatisticasDTO.setMin(menorValorBigDecimal.doubleValue());
                estatisticasDTO.setMax(maiorValorBigDecimal.doubleValue());
            }
        }
          return estatisticasDTO;
        }
    }