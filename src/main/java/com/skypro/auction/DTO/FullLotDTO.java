package com.skypro.auction.DTO;

import com.skypro.auction.enums.Status;
import com.skypro.auction.model.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class FullLotDTO {
    private Long id;
    private Status status = Status.CREATED;
    private String title;
    private String  description;
    private Integer startPrice;
    private Integer bidPrice;
    private Integer currentPrice;
    private BidDTO lastBid;


    public static FullLotDTO fromLot(Lot lot){
        var dto = new FullLotDTO();
        BeanUtils.copyProperties(lot, dto);
        return dto;
    }

}
