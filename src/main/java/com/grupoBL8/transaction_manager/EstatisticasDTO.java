package com.grupoBL8.transaction_manager;

import lombok.Data;

@Data
public class EstatisticasDTO {

    private int count;
    private double sum;
    private double avg;
    private double min;
    private double max;


   public EstatisticasDTO(int count, double sum, double avg, double min, double max){
       this.count = count;
       this.sum = sum;
       this.avg = avg;
       this.min = min;
       this.max = max;
   }
   public EstatisticasDTO(){

   }

}
