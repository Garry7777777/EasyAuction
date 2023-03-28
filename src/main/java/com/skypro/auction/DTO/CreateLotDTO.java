package com.skypro.auction.DTO;

import com.skypro.auction.model.Lot;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CreateLotDTO {
    private String title;
    private String  description;
    private Integer startPrice;
    private Integer bidPrice;


    public static CreateLotDTO fromLot(Lot lot){
        var dto = new CreateLotDTO();
        BeanUtils.copyProperties(lot, dto);
        return dto;
    }
    public Lot toLot(){
        Lot lot = new Lot();
        BeanUtils.copyProperties(this, lot );
        return lot;
    }
}
