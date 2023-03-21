package com.skypro.auction.DTO;

import com.skypro.auction.enums.Status;
import com.skypro.auction.model.Lot;
import lombok.Data;
import org.springframework.beans.BeanUtils;

// /lot/export  Экспортировать все лоты в файл CSV
// Экспортировать все лоты в формате
// id,title,status,lastBidder,currentPrice в одном файле CSV

@Data
public class ExportLotDTO {
    private Long id;
    private String title;
    private Status status;
    private String lastBidder;
    private Integer currentPrice;

    public static ExportLotDTO fromLot(Lot lot){
        var dto = new ExportLotDTO();
        BeanUtils.copyProperties(lot, dto);
        return dto;
    }

}
