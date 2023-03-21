package com.skypro.auction.DTO;

import com.skypro.auction.model.Bid;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import java.time.LocalDateTime;

@Data
public class BidDTO {
    private String bidder;
    private LocalDateTime date;

    public static BidDTO fromBid(Bid bid){
        var dto = new BidDTO();
        BeanUtils.copyProperties(bid, dto);
        return dto;
    }
}
