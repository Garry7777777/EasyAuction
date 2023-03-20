package com.skypro.auction.model;


import com.skypro.auction.enums.Status;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Lot {
    @Id
    @GeneratedValue
    private Long id;
    private Status status;
    private String title;
    @Column(length = 4096)
    private String  description;
    private Integer startPrice;
    private Integer bidPrice;
    private Integer currentPrice;
    @OneToMany (mappedBy = "lot",cascade = CascadeType.REMOVE )
    private List<Bid> lastBid;
}
