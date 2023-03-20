package com.skypro.auction.DTO;

import com.skypro.auction.model.Bid;
import org.springframework.beans.BeanUtils;
import java.time.LocalDateTime;

public class BidDTO {
    private String bidder;
    private LocalDateTime date;

    public static BidDTO fromBid(Bid bid){
        var dto = new BidDTO();
        BeanUtils.copyProperties(bid, dto);
        return dto;
    }
    public Bid toBid(){
        Bid bid = new Bid();
        BeanUtils.copyProperties(this, bid );
        return bid;
    }
}
