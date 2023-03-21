package com.skypro.auction.service;

import com.skypro.auction.DTO.BidDTO;
import com.skypro.auction.model.*;
import com.skypro.auction.enums.Status;
import com.skypro.auction.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class BidService {

    @Autowired
    LotRepository lotRepository;
    @Autowired
    BidRepository bidRepository;

    public Status createBid(Long id, String name) {

        Lot lot = lotRepository.findById(id).orElse(null);
        if (lot == null) return null;
        if (lot.getStatus() != Status.STARTED) return lot.getStatus();
        bidRepository.save(new Bid(name, LocalDateTime.now(), lotRepository.findById(id).get()));
        return Status.STARTED;
    }

    public BidDTO getFirstByLotId(Long id) {
        return bidRepository.findByLotIdOrderByDateAsc(id).stream().findFirst().map(BidDTO::fromBid).get();
    }

    public BidDTO getFrequentByLotId(Long id) {
    return bidRepository.findByBidderAndLot_IdOrderByDateDesc(bidRepository.findFrequentByLotId(id),id).
            stream().findFirst().map(BidDTO::fromBid).get();
    }
}
