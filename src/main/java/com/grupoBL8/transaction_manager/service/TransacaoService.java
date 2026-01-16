package com.grupoBL8.transaction_manager.service;

import com.grupoBL8.transaction_manager.model.TransacaoModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TransacaoService {
    List<TransacaoModel> listTrasacao = new ArrayList<>();

    //Criar
    public TransacaoModel criar(@RequestBody TransacaoModel transacaoModel){
        listTrasacao.add(transacaoModel);
        return transacaoModel;
    }

    // Lisatar
     public List<TransacaoModel> listarTodos(){
        return listTrasacao;
     }

     // Estatisticas
    public String estatisticas(){

    // Cout:
     BigDecimal count = BigDecimal.valueOf(listTrasacao.size());

     // Soma:
    BigDecimal soma = listTrasacao.stream()
            .map(TransacaoModel::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    // Media
    BigDecimal media = soma.divide(count);

    // Min
    BigDecimal menor = listTrasacao.stream()
            .map(TransacaoModel::getValor)
            .min(Comparator.naturalOrder())
            .orElse(BigDecimal.ZERO);

    // Max
        BigDecimal maior = listTrasacao.stream()
                .map(TransacaoModel::getValor)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);

        return "count: " + count + "\n" +
        "sum: " + soma + "\n" +
        "avg: " + media + "\n" +
        "min: " + menor + "\n" +
        "max: " + maior + "\n";
    }

}