package com.skypro.auction.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
    private String bidder;
    @Id
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.LAZY)
    private Lot lot;
}
