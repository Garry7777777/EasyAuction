package com.skypro.auction.DTO;

import com.skypro.auction.enums.Status;
import com.skypro.auction.model.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class LotDTO {
    private Long id;
    private Status status;
    private String title;
    private String  description;
    private Integer startPrice;
    private Integer bidPrice;


    public static LotDTO fromLot(Lot lot){
        var dto = new LotDTO();
        BeanUtils.copyProperties(lot, dto);
        return dto;
    }
}
