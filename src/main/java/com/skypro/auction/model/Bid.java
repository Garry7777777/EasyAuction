package com.skypro.auction.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
public class Bid {
    @Id
    @GeneratedValue
    private Long id;
    private String bidder;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    private Lot lot;
}
