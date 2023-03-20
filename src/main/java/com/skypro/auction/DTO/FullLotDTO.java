package com.skypro.auction.DTO;

import com.skypro.auction.enums.Status;
import com.skypro.auction.model.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class FullLotDTO {
    private Long id;
    private Status status = Status.CREATED;
    private String title;
    private String  description;
    private Integer startPrice;
    private Integer bidPrice;
    private Integer currentPrice;
    private List<BidDTO> lastBid;


    public static FullLotDTO fromLot(Lot lot){
        var dto = new FullLotDTO();
        BeanUtils.copyProperties(lot, dto);
        return dto;
    }
    public Lot toLot(){
        Lot lot = new Lot();
        BeanUtils.copyProperties(this, lot );
        return lot;
    }
}
