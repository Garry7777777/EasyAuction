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

        var lot = lotRepository.findById(id);
        if (lot.isEmpty()) return null;
        if (lot.get().getStatus() != Status.STARTED) return lot.get().getStatus();
        bidRepository.save(new Bid(name, LocalDateTime.now(), lot.get()));
        return Status.STARTED;
    }

    public BidDTO getFirstByLotId(Long id) {
        return bidRepository.findByLotIdOrderByDateAsc(id)
                .stream().findFirst().map(BidDTO::fromBid).orElse(null);
    }

    public BidDTO getFrequentByLotId(Long id) {
    return bidRepository.findByBidderAndLot_IdOrderByDateDesc(bidRepository.findFrequentByLotId(id),id).
            stream().findFirst().map(BidDTO::fromBid).orElse(null);
    }
}
