package com.grupoBL8.transaction_manager.estatisticas;

import lombok.Data;

@Data
public class EstatisticasDTO {

    private Long count;
    private Double sum;
    private Double avg;
    private Double min;
    private Double max;


   public EstatisticasDTO(Long count, Double sum, Double avg, Double min, Double max){
       this.count = count;
       this.sum = sum;
       this.avg = avg;
       this.min = min;
       this.max = max;
   }
   public EstatisticasDTO(){

   }

}
