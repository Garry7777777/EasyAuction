package com.skypro.auction.DTO;

import com.skypro.auction.model.Bid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidDTO {
    private String bidder;
    private LocalDateTime date;


    public static BidDTO fromBid(Bid bid){
        var dto = new BidDTO();
        BeanUtils.copyProperties(bid, dto);
        return dto;
    }
}
