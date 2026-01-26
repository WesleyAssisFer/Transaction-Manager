package com.grupoBL8.transaction_manager;

import com.grupoBL8.transaction_manager.model.TransacaoModel;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {

    public TransacaoModel map(TransacaoDTO transacaoDTO){
        TransacaoModel model = new TransacaoModel();
        model.setValor(transacaoDTO.getValor());
        model.setDataHora(transacaoDTO.getDataHora());

        return  model;
    }

    public TransacaoDTO map(TransacaoModel transacaoModel){
        TransacaoDTO dto = new TransacaoDTO();
        dto.setValor(transacaoModel.getValor());
        dto.setDataHora(transacaoModel.getDataHora());

        return dto;
    }

}
