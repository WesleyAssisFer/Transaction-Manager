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
        // Pega o horaio atual menos 1 min, caso queira colocar mais tempo, basta aumentar o minutos.
        OffsetDateTime umMinutoAtras = horarioAtual.minusMinutes(1);

        // Listas que salvam os dados da TransacaoModel.
        List<TransacaoModel> listaValoreDataHora = transacaoRepository.listarTodos();
        List<BigDecimal> valoresListaBigDecimal = new ArrayList<>();
        List<TransacaoModel> listaValoresEDataValida = new ArrayList<>();

        for(TransacaoModel valoresEData : listaValoreDataHora){

            OffsetDateTime dataHora = valoresEData.getDataHora();

            if((dataHora.isBefore(horarioAtual)) && (dataHora.isAfter(umMinutoAtras) || dataHora.isEqual(umMinutoAtras))) {

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