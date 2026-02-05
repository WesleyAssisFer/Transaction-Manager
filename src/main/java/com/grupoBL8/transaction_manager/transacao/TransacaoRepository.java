package com.grupoBL8.transaction_manager.transacao;

import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class TransacaoRepository {

    List<TransacaoModel> listTrasacao = new ArrayList<>();

    // Criar
    public TransacaoModel salvarDados(TransacaoModel transacaoModel){
        listTrasacao.add(transacaoModel);
        return null;
    }

    // Listar toda a lista
    public List<TransacaoModel> listarTodos(){
       return listTrasacao;
    }

    // Apagar
    public void limparDados(){
        listTrasacao.clear();
    }



}
